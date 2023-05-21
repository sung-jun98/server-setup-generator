package server_setup_generator;
import java.util.Map;
//클라이언트에서 캔버스로 도식도를 그렸다면 그 데이터를 저장할 자바 객체
public class OperatorData {
	
	private FlowchartData flowchartData;

    public FlowchartData getFlowchartData() {
        return flowchartData;
    }

    public void setFlowchartData(FlowchartData flowchartData) {
        this.flowchartData = flowchartData;
    }
	
}
//선택문 예시) flowchartData.getFlowchartData().getOperators().get("operator1").getProperties().getTitle()
class FlowchartData{
	private Map<String, Operator> operators;
    private Map<String, Link> links;
    private Map<String, Object> operatorTypes;//이건 안쓸


    public Map<String, Operator> getOperators() {
        return operators;
    }

    public void setOperators(Map<String, Operator> operators) {
        this.operators = operators;
    }

    public Map<String, Link> getLinks() {
        return links;
    }

    public void setLinks(Map<String, Link> links) {
        this.links = links;
    }
	
}

class Operator {
    private int top;
    private int left;
    private Properties properties;

    public int getTop() {
        return top;
    }

    public void setTop(int top) {
        this.top = top;
    }

    public int getLeft() {
        return left;
    }

    public void setLeft(int left) {
        this.left = left;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }
}

class Properties {
    private String title;
    private Map<String, Input> inputs;
    private Map<String, Output> outputs;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Map<String, Input> getInputs() {
        return inputs;
    }

    public void setInputs(Map<String, Input> inputs) {
        this.inputs = inputs;
    }

    public Map<String, Output> getOutputs() {
        return outputs;
    }

    public void setOutputs(Map<String, Output> outputs) {
        this.outputs = outputs;
    }
}

class Input {
    private String label;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}

class Output {
    private String label;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}

class Link {
    private String fromOperator;
    private String fromConnector;
    private String toOperator;
    private String toConnector;

    public String getFromOperator() {
        return fromOperator;
    }

    public void setFromOperator(String fromOperator) {
        this.fromOperator = fromOperator;
    }

    public String getFromConnector() {
        return fromConnector;
    }

    public void setFromConnector(String fromConnector) {
        this.fromConnector = fromConnector;
    }

    public String getToOperator() {
        return toOperator;
    }

    public void setToOperator(String toOperator) {
        this.toOperator = toOperator;
    }

    public String getToConnector() {
        return toConnector;
    }

    public void setToConnector(String toConnector) {
        this.toConnector = toConnector;
    }
}
