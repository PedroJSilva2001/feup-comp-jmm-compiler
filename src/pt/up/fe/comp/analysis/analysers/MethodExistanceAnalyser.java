package pt.up.fe.comp.analysis.analysers;

import pt.up.fe.comp.analysis.PreorderSemanticAnalyser;
import pt.up.fe.comp.analysis.table.AstNode;
import pt.up.fe.comp.analysis.table.Method;
import pt.up.fe.comp.analysis.table.SymbolTableImpl;
import pt.up.fe.comp.jmm.ast.JmmNode;
import pt.up.fe.comp.jmm.report.Report;
import pt.up.fe.comp.jmm.report.ReportType;
import pt.up.fe.comp.jmm.report.Stage;

import java.util.Objects;

public class MethodExistanceAnalyser extends PreorderSemanticAnalyser {
    public MethodExistanceAnalyser(){
        super();
        addVisit(AstNode.THIS, this::visitThis);
        addVisit(AstNode.EXPRESSION_DOT, this::visitExpressionDot);
    }

    public Integer visitThis(JmmNode node, SymbolTableImpl symbolTable){

        Method method = symbolTable.findMethod(node.getJmmParent().getJmmChild(1).get("name"));

        // TODO assume super has
        // TODO also check for class fields

        // if method isn't implemented in the class and doesn't have a super class
        if(method == null && symbolTable.getSuper() == null) {
            addReport(new Report(
                    ReportType.ERROR, Stage.SEMANTIC,
                    Integer.parseInt(node.get("line")),
                    Integer.parseInt(node.get("col")),
                    "Method " + node.getJmmParent().getJmmChild(1).get("name") +
                            " is not implemented in this class."));
        }

        return 0;
    }

    public Integer visitExpressionDot(JmmNode node, SymbolTableImpl symbolTable){

        // TODO assume super has
        // TODO assume import has method


        if (this.getJmmNodeType(node.getJmmChild(0), symbolTable).getName().equals(symbolTable.getClassName())){

            JmmNode analyse_node;

            if(Objects.equals(node.getJmmChild(1).getKind(), AstNode.EXPRESSION_NEW)){
                analyse_node = node.getJmmChild(1).getJmmChild(0);
            } else {
                analyse_node = node.getJmmChild(1);
            }

            Method method = symbolTable.findMethod(analyse_node.get("name"));

            // if method isn't implemented in the class and doesn't have a super class
            if(method == null && symbolTable.getSuper() == null) {
                addReport(new Report(
                        ReportType.ERROR, Stage.SEMANTIC,
                        Integer.parseInt(node.get("line")),
                        Integer.parseInt(node.get("col")),
                        "Method is not implemented in this class."));
            }
        }
        return 0;
    }
}
