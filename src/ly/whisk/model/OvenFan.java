package ly.whisk.model;



public class OvenFan { 
  /**
   * required: false
   **/
  private String value = null;
  
  public enum valueEnum {  Off,  Low,  High,  }; 
  
  
  public String getValue() {
    return value;
  }
  public void setValue(String value) {
    this.value = value;
  }

  

  @Override
  public String toString()  {
    StringBuilder sb = new StringBuilder();
    sb.append("class OvenFan {\n");
    sb.append("  value: ").append(value).append("\n");
    sb.append("}\n");
    return sb.toString();
  }
}
