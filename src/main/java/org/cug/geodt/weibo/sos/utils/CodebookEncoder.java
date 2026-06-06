package org.cug.geodt.weibo.sos.utils;

import org.cug.geodt.weibo.sos.exception.BusinessException;
import org.cug.geodt.weibo.sos.exception.Code;
import org.cug.geodt.weibo.sos.pojo.sensor.data.SensorData;
import org.cug.geodt.weibo.sos.pojo.sensor.data.SensorDataFloat;
import org.cug.geodt.weibo.sos.pojo.sensor.data.SensorDataString;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class CodebookEncoder {
    static Map<Class<?>, Map<String, Method>> fieldGettersCache;
    static Map<Class<?>, Map<String, Class>> actualFieldTypes;

    static {
        fieldGettersCache = new HashMap<>();
        actualFieldTypes = new HashMap<>();
    }

    public static <T> EncodeDataset encodeSensorInfos(List<T> sensorInfos, List<String> needNames) {
        if(sensorInfos == null || sensorInfos.isEmpty()){
            throw new BusinessException("查询结果为null", Code.BUSINESS_ERR);
        }
        Class dataHolderClass = sensorInfos.get(0).getClass();
        if(dataHolderClass == SensorDataString.class || dataHolderClass == SensorDataFloat.class) {
            dataHolderClass = SensorData.class;
        }
        Map<String, Method> fields = extractFields(dataHolderClass);
        Map<String, Set<Integer>> intDistinctValues = new HashMap<>();
        Map<String,Set<Long>> longDistinctValues = new HashMap<>();
        Map<String, Set<Float>> floatDistinctValues = new HashMap<>();
        Map<String, Set<String>> stringDistinctValues = new HashMap<>();
        Map<String, Set<Double>> doubleDistinctValues = new HashMap<>();
//        Map<String, Set<Object>> objectDistinctValues = new HashMap<>();

        EncodeDataset encodeDataset = new EncodeDataset();
        for (T sensor : sensorInfos) {
            for (String needName : needNames) {
                Method method = fields.get(needName);
                try {
                    //invoke 反射
                    Object invokeValue = method.invoke(sensor);
//                    Class returnType = computeReturnType(method.getReturnType());
                    Boolean isSuccess = handeInvokeValue(
                            intDistinctValues,
                            longDistinctValues,
                            floatDistinctValues,
                            stringDistinctValues,
                            doubleDistinctValues,
                            needName,
                            dataHolderClass,
                            method.getReturnType(),//class
                            invokeValue);
                } catch (IllegalAccessException | InvocationTargetException | NullPointerException ignored) {
                }
            }
        }

        for (T sensor : sensorInfos) {

            //字典编码
            EncodedRecord encodedRecord = new EncodedRecord();
            for (int i = 0; i < needNames.size(); i++) {
                String fieldName = needNames.get(i);
                int codebookIdx = i;
                // 属性获取器
                Method method = fields.get(fieldName);
                Class fieldType = actualFieldTypes.get(dataHolderClass).get(fieldName);
                try {
                    Object invokeValue = method.invoke(sensor);
                    if(fieldType == Object.class){
                        fieldType = invokeValue.getClass();
                    }
                    if (fieldType.equals(Float.class)) {
                        Float invokeResult = (Float) method.invoke(sensor);
                        CodePoint1 codePoint1 = new CodePoint1(fieldName, invokeResult);
                        encodedRecord.addCodePoint(codePoint1);
                    } else if (fieldType.equals(Integer.class)) {
                        Integer invokeResult = (Integer) method.invoke(sensor);
                        int valueIdx = new ArrayList<>(intDistinctValues.get(fieldName)).indexOf(invokeResult);
                        CodePoint codePoint = new CodePoint(codebookIdx, valueIdx);
                        encodedRecord.addCodePoint(codePoint);
                    } else if (fieldType.equals(String.class)) {
                        String invokeResult = (String) method.invoke(sensor);
                        int valueIdx = new ArrayList<>(stringDistinctValues.get(fieldName)).indexOf(invokeResult);
                        CodePoint codePoint = new CodePoint(codebookIdx, valueIdx);
                        encodedRecord.addCodePoint(codePoint);
                    } else if (fieldType.equals(Double.class)) {
                        Double invokeResult = (Double) method.invoke(sensor);
                        CodePoint1 codePoint1 = new CodePoint1(fieldName, invokeResult);
                        encodedRecord.addCodePoint(codePoint1);
                    }else if (fieldType.equals(Long.class)){
                        Long invokeResult = (Long) method.invoke(sensor);
                        int valueIdx = new ArrayList<>(longDistinctValues.get(fieldName)).indexOf(invokeResult);
                        CodePoint codePoint1 = new CodePoint(codebookIdx, valueIdx);
                        encodedRecord.addCodePoint(codePoint1);
                    }

                } catch (IllegalAccessException | IllegalArgumentException illegalAccessException) {

                } catch (InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
            }
            encodeDataset.addEncodedRecord(encodedRecord);
        }

        //获取字段名 对应的返回值的类型
        HashMap<String, Class<?>> fieldTypes = new HashMap<>();
        for (int i = 0; i < needNames.size(); i++) {
            fieldTypes.put(needNames.get(i), fields.get(needNames.get(i)).getReturnType());
        }
        //建立codebook
        List<Codebook> codebooks = buildCodebooks(needNames,
                fieldTypes,
                dataHolderClass,
                floatDistinctValues,
                intDistinctValues,
                longDistinctValues,
                stringDistinctValues,
                doubleDistinctValues);

        encodeDataset.setCodebooks(codebooks);
        return encodeDataset;
    }

    //通过获取不同类的方法Map<方法名, 方法>
    public static Map<String, Method> extractFields(Class<?> classInfo) {
        if (fieldGettersCache.containsKey(classInfo)) {
            return fieldGettersCache.get(classInfo);
        }
        //获取该类及超类Object的公共方法
        Method[] methods = classInfo.getMethods();
//        Map<String, Method> nameList = new HashMap<>();
        for (Method method : methods) {
            String methodName = method.getName();
            if (methodName.startsWith("get")) {
                String fieldName = lowerFirstCharacter(methodName.substring(3));
//                nameList.put(fieldName, method);
                if (!fieldGettersCache.containsKey(classInfo)) {
                    fieldGettersCache.put(classInfo, new HashMap<>());
                }
                if (!fieldGettersCache.get(classInfo).containsKey(fieldName)) {
                    fieldGettersCache.get(classInfo).put(fieldName, method);
                }
            }
        }
        return fieldGettersCache.get(classInfo);
    }

    //fieldName首字母改为小写
    private static String lowerFirstCharacter(String s) {
        StringBuilder sb = new StringBuilder();
        sb.append(Character.toLowerCase(s.charAt(0)));
        sb.append(s.substring(1));
        return sb.toString();
    }

    //将利用反射 invoke的sensor的值填到对应的set里面
    private static Boolean handeInvokeValue(Map<String, Set<Integer>> intDistinctValues,
                                            Map<String, Set<Long>> longDistinctValues,
                                            Map<String, Set<Float>> floatDistinctValues,
                                            Map<String, Set<String>> stringDistinctValues,
                                            Map<String, Set<Double>> doubleDistinctValues,
                                            String fieldName,
                                            Class<?> parentClass,
                                            Class<?> rawFieldType,
                                            Object invokeValue) {
        Boolean isSuccess = false;
        //fieldType.equals(Float.class);
        Class fieldType = null;

        if(!actualFieldTypes.containsKey(parentClass)) {
            actualFieldTypes.put(parentClass, new HashMap<>());
        }

//        if(actualFieldTypes.get(parentClass).containsKey(fieldName)) {
//            fieldType = actualFieldTypes.get(parentClass).get(fieldName);
//        }else {
//            if(rawFieldType == Object.class) {
//                fieldType = invokeValue.getClass();
//            }else {
//                fieldType = rawFieldType;
//            }
//            actualFieldTypes.get(parentClass).put(fieldName, fieldType);
//        }

        if(actualFieldTypes.get(parentClass).containsKey(fieldName)) {
                fieldType = actualFieldTypes.get(parentClass).get(fieldName);
        }else {
                fieldType = rawFieldType;
            actualFieldTypes.get(parentClass).put(fieldName, fieldType);
        }

        if(fieldType == Object.class){
            fieldType = invokeValue.getClass();
        }
        if (fieldType.equals(Double.class) ) {
            Double value = (Double) invokeValue;
            if (!doubleDistinctValues.containsKey(fieldName)) {
                doubleDistinctValues.put(fieldName, new HashSet<>());
            }
            doubleDistinctValues.get(fieldName).add(value);
            isSuccess = true;
        } else if (fieldType.equals(Integer.class)) {
            Integer value = (Integer) invokeValue;
            if (!intDistinctValues.containsKey(fieldName)) {
                intDistinctValues.put(fieldName, new HashSet<>());
            }
            intDistinctValues.get(fieldName).add(value);
            isSuccess = true;
        } else if (fieldType.equals(String.class)) {
            String value = (String) invokeValue;
            if (!stringDistinctValues.containsKey(fieldName)) {
                stringDistinctValues.put(fieldName, new HashSet<>());
            }
            stringDistinctValues.get(fieldName).add(value);
            isSuccess = true;
        } else if (fieldType.equals(Float.class)) {
            Float value = (Float) invokeValue;
            if (!floatDistinctValues.containsKey(fieldName)) {
                floatDistinctValues.put(fieldName, new HashSet<>());
            }
            floatDistinctValues.get(fieldName).add(value);
            isSuccess = true;
        }else if (fieldType.equals(Long.class)){
            Long value = (Long) invokeValue;
            if (!longDistinctValues.containsKey(fieldName)) {
                longDistinctValues.put(fieldName, new HashSet<>());
            }
            longDistinctValues.get(fieldName).add(value);
            isSuccess = true;
        }
        return isSuccess;
    }

    //对返回Json的schema填值
    public static List<Codebook> buildCodebooks(List<String> fieldNames,
                                                Map<String, Class<?>> fieldTypes,
                                                Class<?> dataHolderClass,
                                                Map<String, Set<Float>> floatCodebooks,
                                                Map<String, Set<Integer>> intCodebooks,
                                                Map<String, Set<Long>> longCodebooks,
                                                Map<String, Set<String>> stringCodebooks,
                                                Map<String, Set<Double>> doubleCodebooks) {
        List<Codebook> codebooks = new ArrayList<>();
        for (int i = 0; i < fieldNames.size(); i++) {
            String fieldName = fieldNames.get(i);
            Class fieldType = actualFieldTypes.get(dataHolderClass).get(fieldName);
            if (fieldType.equals(Float.class)){
                List<Float> floats = new ArrayList<>(floatCodebooks.get(fieldNames.get(i)));
                Codebook codebook = new Codebook(fieldNames.get(i), "float", null);
                codebooks.add(codebook);
            } else if (fieldType.equals(Integer.class)) {
                List<Integer> integers = new ArrayList<>(intCodebooks.get(fieldNames.get(i)));
                Codebook codebook = new Codebook(fieldNames.get(i), "integer", integers);
                codebooks.add(codebook);
            } else if (fieldType.equals(String.class)) {
                List<String> strings = new ArrayList<>(stringCodebooks.get(fieldNames.get(i)));
                Codebook codebook = new Codebook(fieldNames.get(i), "string", strings);
                codebooks.add(codebook);
            } else if (fieldType.equals(Double.class)) {
                List<Double> doubles = new ArrayList<>(doubleCodebooks.get(fieldNames.get(i)));
                Codebook codebook = new Codebook(fieldNames.get(i), "double", null);
                codebooks.add(codebook);
            } else if (fieldType.equals(Long.class)){
                List<Long> longs = new ArrayList<>(longCodebooks.get(fieldNames.get(i)));
                Codebook codebook = new Codebook(fieldNames.get(i), "long", longs);
                codebooks.add(codebook);
            }else if (fieldType.equals(Object.class)) {
                List<String> strings = null;
                try {
                    strings = new ArrayList<>(stringCodebooks.get(fieldNames.get(i)));
                }catch (NullPointerException e){
                    e.printStackTrace();
                }
                Codebook codebook = new Codebook(fieldNames.get(i), "object", strings);
                codebooks.add(codebook);
            }
        }
        return codebooks;
    }

    //dataSet
    public static class EncodeDataset {
        public List<Codebook> codebooks;
        public List<EncodedRecord> encodedRecords;

        public EncodeDataset() {
            this.encodedRecords = new ArrayList<>();
        }


        public void addEncodedRecord(EncodedRecord encodedRecord) {
            this.encodedRecords.add(encodedRecord);
        }

        public void setCodebooks(List<Codebook> codebooks) {
            this.codebooks = codebooks;
        }
    }

    //schema
    public static class Codebook {
        public String fieldName;
        public String fieldType;
        public List<?> codebook;


        public Codebook(String fieldName, String fieldType, List<?> codebook) {
            this.fieldName = fieldName;
            this.fieldType = fieldType;
            this.codebook = codebook;
        }
    }

    //data
    public static class EncodedRecord {
        public List<CodePoints> codePoints;

        public EncodedRecord() {
            codePoints = new ArrayList<>();
        }

        public void addCodePoint(CodePoints codePoint) {
            this.codePoints.add(codePoint);
        }

    }

    //innerData

    public static interface CodePoints<T>{
    }
    public static class CodePoint<T> implements CodePoints{
        public Integer codebookIdx;
        public T valueIdx;


        public CodePoint(int codebookIdx, T valueIdx) {
            this.codebookIdx = codebookIdx;
            this.valueIdx = valueIdx;
        }
    }

    public static class CodePoint1<T> implements CodePoints{
        public T fieldName;
        public T value;

        public CodePoint1(T fieldName, T value) {
            this.fieldName = fieldName;
            this.value = value;
        }
    }


}

