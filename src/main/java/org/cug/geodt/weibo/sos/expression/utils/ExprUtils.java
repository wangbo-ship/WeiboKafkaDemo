package org.cug.geodt.weibo.sos.expression.utils;

import org.cug.geodt.weibo.sos.expression.node.BooleanNode;
import org.cug.geodt.weibo.sos.expression.node.ExprNode;
import org.cug.geodt.weibo.sos.expression.node.Imp.BooleanFalseNode;
import org.cug.geodt.weibo.sos.expression.node.Imp.BooleanTrueNode;
import org.cug.geodt.weibo.sos.expression.node.Imp.GeometryNode;
import org.cug.geodt.weibo.sos.expression.node.ValueNode;
import org.cug.geodt.weibo.sos.utils.ConsoleUtils;
import org.cug.geodt.weibo.sos.utils.DateUtils;

import java.util.regex.Pattern;

/**
 * @author mengquanqi
 * @version V1.0
 * @Package com.cug.geodt.filter
 * @Description
 * @date 2023/1/4 15:58
 */
public class ExprUtils {

    static public BooleanNode gte(ExprNode leftNode, ExprNode rightNode, String targetFieldName){
        ValueNode l = (ValueNode) leftNode;
        ValueNode r = (ValueNode) rightNode;
        if (l==null||r==null||l.getValue()==null||r.getValue()==null){
            return BooleanFalseNode.INSTANCE;
        }
        if(targetFieldName.equals("time") || targetFieldName.equals("obsTimestamp")) {
            //如果是时间判断
            String date = l.getValue().toString();
            Long timestamp = DateUtils.dateToLong(date);
            Long targetTime = Long.parseLong(String.valueOf(r.getValue()));
            if ( targetTime >= timestamp ) {
                return BooleanTrueNode.INSTANCE;
            } else {
                return BooleanFalseNode.INSTANCE;
            }
        }else {
            if (Float.parseFloat(r.getValue().toString()) >= Float.parseFloat(l.getValue().toString())){
                return BooleanTrueNode.INSTANCE;
            } else {
                return BooleanFalseNode.INSTANCE;
            }
        }
    }

    static public BooleanNode equal(ExprNode leftNode,ExprNode rightNode,String targetFieldName){
        ValueNode l = (ValueNode) leftNode;
        ValueNode r = (ValueNode) rightNode;
        if (l==null||r==null||l.getValue()==null||r.getValue()==null){
            return BooleanFalseNode.INSTANCE;
        }
        if(targetFieldName.equals("time")|| targetFieldName.equals("obsTimestamp")){
            //如果是时间判断
            String date = l.getValue().toString();
            Long timestamp = DateUtils.dateToLong(date);
            Long targetTime = Long.parseLong(String.valueOf(r.getValue()));
            if(timestamp.equals(targetTime)){
                return BooleanTrueNode.INSTANCE;
            }else {
                return BooleanFalseNode.INSTANCE;
            }
        }else if(targetFieldName.equals("geometry")){
            GeometryNode lNode = (GeometryNode) l;
            GeometryNode rNode = (GeometryNode) r;
            if (lNode.getValue().equals(rNode.getValue())){
                return BooleanTrueNode.INSTANCE;
            }else {
                return BooleanFalseNode.INSTANCE;
            }
        } else {
            if(l.getValue().toString().equals(r.getValue().toString())){
                return BooleanTrueNode.INSTANCE;
            }else {
                return BooleanFalseNode.INSTANCE;
            }
        }
    }

    public static BooleanNode gt(ExprNode leftNode,ExprNode rightNode,String targetFieldName) {
        ValueNode l = (ValueNode) leftNode;
        ValueNode r = (ValueNode) rightNode;
        if (l==null||r==null||l.getValue()==null||r.getValue()==null){
            return BooleanFalseNode.INSTANCE;
        }
        if(targetFieldName.equals("time")|| targetFieldName.equals("obsTimestamp")) {
            //如果是时间判断
            String date = l.getValue().toString();
            Long timestamp = DateUtils.dateToLong(date);
            Long targetTime = Long.parseLong(String.valueOf(r.getValue()));
            if (targetTime > timestamp) {
                return BooleanTrueNode.INSTANCE;
            } else {
                return BooleanFalseNode.INSTANCE;
            }
        }else {
            if (Float.parseFloat(r.getValue().toString())>Float.parseFloat(l.getValue().toString())){
                return BooleanTrueNode.INSTANCE;
            } else {
                return BooleanFalseNode.INSTANCE;
            }
        }
    }

    public static BooleanNode lt(ExprNode leftNode,ExprNode rightNode,String targetFieldName) {
        ValueNode l = (ValueNode) leftNode;
        ValueNode r = (ValueNode) rightNode;
        if (l==null||r==null||l.getValue()==null||r.getValue()==null){
            return BooleanFalseNode.INSTANCE;
        }
        if(targetFieldName.equals("time")|| targetFieldName.equals("obsTimestamp")) {
            //如果是时间判断
            String date = l.getValue().toString();
            Long timestamp = DateUtils.dateToLong(date);
            Long targetTime = Long.parseLong(String.valueOf(r.getValue()));
            if (targetTime < timestamp) {
                return BooleanTrueNode.INSTANCE;
            } else {
                return BooleanFalseNode.INSTANCE;
            }
        }else {
            if (Float.parseFloat(r.getValue().toString())<Float.parseFloat(l.getValue().toString())){
                return BooleanTrueNode.INSTANCE;
            } else {
                return BooleanFalseNode.INSTANCE;
            }
        }
    }

    public static BooleanNode lte(ExprNode leftNode,ExprNode rightNode,String targetFieldName) {
        ValueNode l = (ValueNode) leftNode;
        ValueNode r = (ValueNode) rightNode;
        if (l==null||r==null||l.getValue()==null||r.getValue()==null){
            return BooleanFalseNode.INSTANCE;
        }
        if(targetFieldName.equals("time")|| targetFieldName.equals("obsTimestamp")) {
            //如果是时间判断
            String date = l.getValue().toString();
            Long startOfDayTimestamp = DateUtils.dateToLong(date);
            Long targetTime = Long.parseLong(String.valueOf(r.getValue()));
            if (targetTime <= startOfDayTimestamp) {
                return BooleanTrueNode.INSTANCE;
            } else {
                return BooleanFalseNode.INSTANCE;
            }
        }else {
            if (Float.parseFloat(l.getValue().toString())>=Float.parseFloat(r.getValue().toString())){
                return BooleanTrueNode.INSTANCE;
            } else {
                return BooleanFalseNode.INSTANCE;
            }
        }
    }

    public static BooleanNode range(ExprNode leftNode,ExprNode rightNode) {
        return BooleanTrueNode.INSTANCE;
    }

    public static BooleanNode in(ExprNode leftNode,ExprNode rightNode,String targetFieldName) {
        return equal(leftNode,rightNode,targetFieldName);
    }

    public static BooleanNode regexLike(ExprNode leftNode,ExprNode rightNode,String targetFieldName) {
        ValueNode l = (ValueNode) leftNode;
        ValueNode r = (ValueNode) rightNode;
        if (l==null||r==null||l.getValue()==null||r.getValue()==null){
            return BooleanFalseNode.INSTANCE;
        }
        try {
            if (Pattern.compile(r.getValue().toString()).matcher(l.getValue().toString()).matches()) {
                return BooleanTrueNode.INSTANCE;
            } else {
                return BooleanFalseNode.INSTANCE;
            }
        }catch (Exception e){
            ConsoleUtils.error("正则表达式出错");
            return BooleanFalseNode.INSTANCE;
        }
    }


    public static BooleanNode and(ExprNode leftNode, ExprNode rightNode) {
        BooleanNode l = (BooleanNode) leftNode.eval(leftNode);
        BooleanNode r = (BooleanNode) rightNode.eval(rightNode);
        if (l==null||r==null||l.getValue()==null||r.getValue()==null){
            return BooleanFalseNode.INSTANCE;
        }
        if(Boolean.compare((l.getValue()&&r.getValue()),true)==0){
            return BooleanTrueNode.INSTANCE;
        }else {
            return  BooleanFalseNode.INSTANCE;
        }
    }

    public static BooleanNode or(ExprNode leftNode, ExprNode rightNode) {
        BooleanNode l = (BooleanNode) leftNode.eval(leftNode);
        BooleanNode r = (BooleanNode) rightNode.eval(rightNode);
        if (l==null||r==null||l.getValue()==null||r.getValue()==null){
            return BooleanFalseNode.INSTANCE;
        }
        if(Boolean.compare((l.getValue()||r.getValue()),true)==0){
            return BooleanTrueNode.INSTANCE;
        }else {
            return  BooleanFalseNode.INSTANCE;
        }
    }

    public static BooleanNode not(ExprNode node) {
        BooleanNode n = (BooleanNode) node.eval(node);
        if (Boolean.compare(n.getValue(),true)==0){
            return BooleanFalseNode.INSTANCE;
        }else {
            return  BooleanTrueNode.INSTANCE;
        }
    }

    public static BooleanNode within(ExprNode leftNode,ExprNode rightNode, String targetFieldName) {
        GeometryNode l = (GeometryNode) leftNode;
        GeometryNode r  = (GeometryNode) rightNode;
        if (l==null||r==null||l.getValue()==null||r.getValue()==null){
            return BooleanFalseNode.INSTANCE;
        }
        if (r.getValue().within(l.getValue())){
            return BooleanTrueNode.INSTANCE;
        }else {
            return BooleanFalseNode.INSTANCE;
        }
    }

    public static BooleanNode contains(ExprNode leftNode, ExprNode rightNode, String targetFieldName) {
        GeometryNode l = (GeometryNode) leftNode;
        GeometryNode r  = (GeometryNode) rightNode;
        if (l==null||r==null||l.getValue()==null||r.getValue()==null){
            return BooleanFalseNode.INSTANCE;
        }
        if (r.getValue().contains(l.getValue())){
            return BooleanTrueNode.INSTANCE;
        }else {
            return BooleanFalseNode.INSTANCE;
        }
    }
}
