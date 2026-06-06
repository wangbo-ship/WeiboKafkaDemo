package org.cug.geodt.weibo.sos.utils.csw;

import org.jdom2.*;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @Classname RecordUtil
 * @Description TODO
 * @Date 2023/8/24 19:43
 * @Created by mjh
 */
public class RecordTransactionUtil {
    Namespace cswNamespace = Namespace.getNamespace("http://www.opengis.net/cat/csw/2.0.2");
    Namespace xsiNamespace = Namespace.getNamespace("xsi", "http://www.w3.org/2001/XMLSchema-instance");
    Namespace dcNamespace = Namespace.getNamespace("dc", "http://purl.org/dc/elements/1.1/");
    Namespace dctNamespace = Namespace.getNamespace("dct", "http://purl.org/dc/terms/");
    Namespace owsNamespace = Namespace.getNamespace("ows", "http://www.opengis.net/ows");
    Namespace ogcNamespace = Namespace.getNamespace("ogc", "http://www.opengis.net/ogc");
    Namespace cugNamespace = Namespace.getNamespace("cug", "http://www.cug.geodt.com/");

    private static final String INSERT = "Insert";
    private static final String UPDATE = "Update";
    private static final String DELETE = "Delete";

    /** 请求的基本路径 */
    private String baseUrl;

    /**元数据标签名 */
    private String metadataName;

    /**元数据标命名空间 */
    private String metadataNameSpace;

    /**元数据的xsd路径 */
    private String metadataUrl;

    public Namespace getCswNamespace() {
        return cswNamespace;
    }

    public void setCswNamespace(Namespace cswNamespace) {
        this.cswNamespace = cswNamespace;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getMetadataName() {
        return metadataName;
    }

    public void setMetadataName(String metadataName) {
        this.metadataName = metadataName;
    }

    public String getMetadataNameSpace() {
        return metadataNameSpace;
    }

    public void setMetadataNameSpace(String metadataNameSpace) {
        this.metadataNameSpace = metadataNameSpace;
    }

    public String getMetadataUrl() {
        return metadataUrl;
    }

    public void setMetadataUrl(String metadataUrl) {
        this.metadataUrl = metadataUrl;
    }

    public RecordTransactionUtil(String baseUrl) {
        this.baseUrl = baseUrl;
        this.metadataName = "CUG_Metadata";
        this.metadataNameSpace = "http://www.cug.geodt.com/";
        this.metadataUrl = "http://192.168.10.11:8888/xml/cugMetadata.xsd";
    }

    public RecordTransactionUtil(String baseUrl, String metadataName) {
        this.baseUrl = baseUrl;
        this.metadataName = metadataName;
        this.metadataNameSpace = "http://www.cug.geodt.com/";
        this.metadataUrl = "http://192.168.10.11:8888/xml/cugMetadata.xsd";
    }

    public RecordTransactionUtil(String baseUrl, String metadataName, String metadataNameSpace, String metadataUrl) {
        this.baseUrl = baseUrl;
        this.metadataName = metadataName;
        this.metadataNameSpace = metadataNameSpace;
        this.metadataUrl = metadataUrl;
    }

    /**
    * @Description 向csw服务添加记录
    * @param recordInfoList csw记录信息集合
    * @date 2023/8/25 10:34
    * @auther mjh
    */
    public void addRecord(List<CSWRecordInfo> recordInfoList) {
        // 构建xml根
        Element root = buildXMLRootEle();
        // 构建Insert节点
        Element insertElement = buildXMLTransactionOperationEle(INSERT);
        root.addContent(insertElement);
        // 构建 元数据节点
        for (CSWRecordInfo recordInfo : recordInfoList) {
            Element cugMetadataElement = buildXMLMetadataEle(metadataName, recordInfo);
            insertElement.addContent(cugMetadataElement);
        }

        Document document = new Document(root);
        // 输出XML文档
        XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat());
        String xml = outputter.outputString(document);
        // 发布请求
        publish(xml);
    }

    /**
    * @Description 根据描述符向csw服务删除一条记录
    * @param identifier 描述符
    * @date 2023/8/25 18:45
    * @auther mjh
    */
    public void deleteByIdentifier(String identifier) {
        // 构建xml根
        Element root = buildXMLRootEle();
        // 构建delete节点
        Element deleteElement = buildXMLTransactionOperationEle(DELETE);
        root.addContent(deleteElement);
        // 构建查询节点
        Element constraintElement = buildXMLConstraintElement(identifier);
        deleteElement.addContent(constraintElement);

        Document document = new Document(root);
        // 输出XML文档
        XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat());
        String xml = outputter.outputString(document);
        // 发布请求
        publish(xml);
    }

    private Element buildXMLConstraintElement(String identifier) {
        // 创建根元素
        Element constraintElement = new Element("Constraint", cswNamespace);
        constraintElement.setAttribute("version", "1.1.0");
        // 创建Filter元素
        Element filterElement = new Element("Filter", ogcNamespace);
        // 创建PropertyIsEqualTo元素
        Element propertyIsEqualToElement = new Element("PropertyIsEqualTo", ogcNamespace);
        // 添加PropertyName元素
        Element propertyNameElement = new Element("PropertyName", ogcNamespace);
        propertyNameElement.setText("identifier");
        propertyIsEqualToElement.addContent(propertyNameElement);
        // 添加Literal元素
        Element literalElement = new Element("Literal", ogcNamespace);
        literalElement.setText(identifier);
        propertyIsEqualToElement.addContent(literalElement);
        // 添加PropertyIsEqualTo元素到Filter元素
        filterElement.addContent(propertyIsEqualToElement);
        // 添加Filter元素到Constraint元素
        constraintElement.addContent(filterElement);
        return constraintElement;
    }

    /** 构建xml的元数据记录element */
    private Element buildXMLMetadataEle(String metadataName, CSWRecordInfo recordInfo) {
        Element cugMetadataElement = new Element(metadataName, cugNamespace);
        cugMetadataElement.addContent(new Element("identifier", dcNamespace).setText(recordInfo.getIdentifier()));
        cugMetadataElement.addContent(new Element("title", dcNamespace).setText(recordInfo.getTitle()));
        cugMetadataElement.addContent(new Element("type", dcNamespace).setText(recordInfo.getType()));
        List<String> subjectList = recordInfo.getSubject();
        if(!Objects.isNull(subjectList) && !subjectList.isEmpty()) {
            subjectList.forEach(subject -> cugMetadataElement.addContent(new Element("subject", dcNamespace).setText(subject)));
        }
        Long modified = recordInfo.getModified();
        if(modified != null) cugMetadataElement.addContent(new Element("modified", dctNamespace).setText(String.valueOf(modified)));
        String creator = recordInfo.getCreator();
        if(creator != null) cugMetadataElement.addContent(new Element("creator", dcNamespace).setText(creator));
        String format = recordInfo.getFormat();
        if(format != null) cugMetadataElement.addContent(new Element("format", dcNamespace).setText(format));
        String aabstract = recordInfo.getAabstract();
        if(aabstract != null) cugMetadataElement.addContent(new Element("abstract", dctNamespace).setText(aabstract));
        String spatial = recordInfo.getSpatial();
        if(spatial != null) cugMetadataElement.addContent(new Element("spatial", dctNamespace).setText(spatial));
        String relation = recordInfo.getRelation();
        if(relation != null) cugMetadataElement.addContent(new Element("relation", dcNamespace).setText(relation));
        String description = recordInfo.getDescription();
        if(description != null) cugMetadataElement.addContent(new Element("description", dcNamespace).setText(description));
        Integer crs = recordInfo.getSrid();
        Double minY = recordInfo.getMinY();
        Double minX = recordInfo.getMinX();
        Double maxY = recordInfo.getMaxY();
        Double maxX = recordInfo.getMaxX();
        if(crs != null && minY != null && minX != null && maxY != null && maxX != null) {
            Element boundingBoxElement = new Element("BoundingBox", owsNamespace);
            boundingBoxElement.setAttribute("crs", "EPSG:" + crs);
            String lower = minY + " " + minX;
            String upper = maxY + " " + maxX;
            boundingBoxElement.addContent(new Element("LowerCorner", owsNamespace).setText(lower));
            boundingBoxElement.addContent(new Element("UpperCorner", owsNamespace).setText(upper));
            cugMetadataElement.addContent(boundingBoxElement);
        }
        return cugMetadataElement;
    }

    /** 构建xml操作的element */
    private Element buildXMLTransactionOperationEle(String OperationType) {
        return new Element(OperationType, cswNamespace);
    }

    /** 构建xml的头部element */
    private Element buildXMLRootEle() {
        Element root = new Element("Transaction", cswNamespace);
        root.addNamespaceDeclaration(cswNamespace);
        root.addNamespaceDeclaration(cswNamespace);
        root.addNamespaceDeclaration(xsiNamespace);
        root.addNamespaceDeclaration(dcNamespace);
        root.addNamespaceDeclaration(dctNamespace);
        root.addNamespaceDeclaration(owsNamespace);
        root.addNamespaceDeclaration(ogcNamespace);
        root.addNamespaceDeclaration(cugNamespace);
        String schema = "http://www.opengis.net/cat/csw/2.0.2 http://schemas.opengis.net/csw/2.0.2/CSW-publication.xsd " + metadataNameSpace + " " + metadataUrl;
        root.setAttribute("schemaLocation", schema, xsiNamespace);
        root.setAttribute("service", "CSW");
        root.setAttribute("version", "2.0.2");
        root.setAttribute("verboseResponse", "false");
        return root;
    }

    /** 发起http的post请求传xml */
    private void publish(String xmlData) {
        try {
            URL url = new URL(baseUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/xml");
            conn.setDoOutput(true);

            OutputStream outputStream = conn.getOutputStream();
            outputStream.write(xmlData.getBytes(StandardCharsets.UTF_8));
            outputStream.flush();
            outputStream.close();

            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                String xmlResponse = response.toString();
                StringReader responseReader = new StringReader(xmlResponse);
                handleXMLResponse(responseReader);
            } else {
                System.out.println("HTTP request failed with response code: " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** 处理返回的xml文档 */
    private void handleXMLResponse(Reader xmlResponseReader) throws IOException, JDOMException {
        SAXBuilder saxBuilder = new SAXBuilder();
        Document document = saxBuilder.build(xmlResponseReader);
        Element root = document.getRootElement();
        if(root.getName().equals("ExceptionReport")) {
            Element exception = root.getChild("Exception", owsNamespace);
            if(!Objects.isNull(exception)) {
                String exceptionCode = exception.getAttribute("exceptionCode").getValue();
                String exceptionText = exception.getChild("ExceptionText", owsNamespace).getContent(0).getValue();
                ExceptionResponseInfo exceptionResponseInfo = new ExceptionResponseInfo(exceptionCode, exceptionText);
                responseHandler.handle(exceptionResponseInfo);
            }
        }
        if(root.getName().equals("TransactionResponse")) {
            TransactionResponseInfo transactionResponseInfo = new TransactionResponseInfo();
            Element summary = root.getChild("TransactionSummary", cswNamespace);
            String totalInserted = summary.getChildText("totalInserted", cswNamespace);
            String totalUpdated = summary.getChildText("totalUpdated", cswNamespace);
            String totalDeleted = summary.getChildText("totalDeleted", cswNamespace);
            transactionResponseInfo.setTotalInserted(Long.valueOf(totalInserted));
            transactionResponseInfo.setTotalUpdated(Long.valueOf(totalUpdated));
            transactionResponseInfo.setTotalDeleted(Long.valueOf(totalDeleted));
            Element insertResult = root.getChild("InsertResult", cswNamespace);
            ArrayList<BriefRecordInfo> briefRecordInfoList = new ArrayList<>();
            if(!Objects.isNull(insertResult)) {
                List<Element> briefRecords = insertResult.getChildren("BriefRecord", cswNamespace);
                for (Element briefRecord : briefRecords) {
                    String identifier = briefRecord.getChild("identifier", dcNamespace).getText();
                    String title = briefRecord.getChild("title", dcNamespace).getText();
                    String type = briefRecord.getChild("type", dcNamespace).getText();
                    Element boundingbox = briefRecord.getChild("BoundingBox", owsNamespace);
                    Double minY = null;
                    Double minX = null;
                    Double maxY = null;
                    Double maxX = null;
                    Integer srid = null;
                    if(!Objects.isNull(boundingbox)) {
                        String lowerCorner = boundingbox.getChildTextTrim("LowerCorner", owsNamespace);
                        String[] lower = lowerCorner.split(" ");
                        minY = Double.parseDouble(lower[0]);
                        minX = Double.parseDouble(lower[1]);
                        String upperCorner = boundingbox.getChildTextTrim("UpperCorner", owsNamespace);
                        String[] upper = upperCorner.split(" ");
                        maxY = Double.parseDouble(upper[0]);
                        maxX = Double.parseDouble(upper[1]);
                        Attribute crs = boundingbox.getAttribute("crs");
                        String crsValue = crs.getValue();
                        if (crsValue.lastIndexOf(":") > 0) {
                            String[] split = crsValue.split(":");
                            crsValue = split[split.length - 1];
                        }
                        srid = Integer.parseInt(crsValue);
                    }
                    BriefRecordInfo briefRecordInfo = (Objects.isNull(boundingbox)) ?
                            new BriefRecordInfo(identifier, title, type) :
                            new BriefRecordInfo(identifier, title, type, srid, minX, minY, maxX, maxY);
                    briefRecordInfoList.add(briefRecordInfo);
                }
            }
            transactionResponseInfo.setInsertResults(briefRecordInfoList);
            responseHandler.handle(transactionResponseInfo);
        }
    }

    public static interface ResponseHandler {
        public void handle(ResponseInfo responseInfo);
    }

    private static class DefaultResponseHandler implements ResponseHandler {

        public static DefaultResponseHandler DEFAULT_RESPONSE_HANDLER = new DefaultResponseHandler();

        private DefaultResponseHandler() {};

        @Override
        public void handle(ResponseInfo responseInfo) {
            String responseType = responseInfo.getResponseType();
            if(responseType.equals("Transaction")) {
                TransactionResponseInfo info = (TransactionResponseInfo) responseInfo;
                System.out.println(info);
            }
            if(responseType.equals("Exception")) {
                ExceptionResponseInfo info = (ExceptionResponseInfo) responseInfo;
                throw new RuntimeException(info.getExceptionText());
            }
        }
    }

    /** 相应结果的处理接口,默认打印结果信息 */
    private ResponseHandler responseHandler = DefaultResponseHandler.DEFAULT_RESPONSE_HANDLER;

    public ResponseHandler getResponseHandler() {
        return responseHandler;
    }

    public void setResponseHandler(ResponseHandler responseHandler) {
        this.responseHandler = responseHandler;
    }

    public static void main(String[] args) {
        // 创建工具类
        RecordTransactionUtil transactionUtil
                = new RecordTransactionUtil("http://192.168.10.12:8180/geoserver/cug_csw");
        // 设置响应结果处理器
        transactionUtil.setResponseHandler(responseInfo -> {
            String responseType = responseInfo.getResponseType();
            if(responseType.equals("Transaction")) {
                TransactionResponseInfo info = (TransactionResponseInfo) responseInfo;
                Long totalInserted = info.getTotalInserted();
                Long totalDeleted = info.getTotalDeleted();
                if(totalInserted != 0) System.out.println("插入的记录总数：" + totalInserted);
                if(totalDeleted != 0) System.out.println("删除的记录总数：" + totalDeleted);
                for (BriefRecordInfo result : info.getInsertResults()) {
                    System.out.println("记录详情：" + result);
                }
            }
            if(responseType.equals("Exception")) {
                ExceptionResponseInfo info = (ExceptionResponseInfo) responseInfo;
                String exceptionText = info.getExceptionText();
                System.out.println("错误原因: " + exceptionText);
            }
        });
        // 删除记录
//        transactionUtil.deleteByIdentifier("sos:" + "urn:groundStation:7");
//        transactionUtil.deleteByIdentifier("sos:" + "urn:satellite:8");
        // 填入信息
        CSWRecordInfo info1 = new CSWRecordInfo();
        info1.setIdentifier("sos:" + "urn:groundStation:7");
        info1.setTitle("新店");
        info1.setSubject(Arrays.asList("ground_station","空气监测站"));
        info1.setType("Service");
        info1.setFormat("SOS");
        info1.setCreator("cug_geodt");
        info1.setModified(Instant.now().toEpochMilli());
        info1.setRelation("sos/getDescribeSensor?procedure=urn:groundStation:7");
        CSWRecordInfo info2 = new CSWRecordInfo();
        info2.setIdentifier("sos:" + "urn:satellite:8");
        info2.setTitle("WFV");
        info2.setSubject(Arrays.asList("imagetype_photographic_sensor","土壤湿度","区域交通"));
        info2.setType("Service");
        info2.setFormat("SOS");
        info2.setCreator("cug_geodt");
        info2.setModified(Instant.now().toEpochMilli());
        info2.setRelation("sos/getDescribeSensor?procedure=urn:satellite:8");
        // 添加记录
        transactionUtil.addRecord(Arrays.asList(info1, info2));

    }
}
