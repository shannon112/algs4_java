/*
This is Shannon's algs4 hw2 at NTU BIME 106-1 PDSA class

- ascii '0'=48 '1'=49 .....'9'=57
- ascii '+'=43 '-'=45 '*'=42 '/'=47'  '('=40  ')'=41
- Integer.valueOf(
- Using while(true){} to detect where is the bug, if have bug judge system will break down
- Using .equals("") to compare two String
*/

public class Calculator {
private static String[] stringArray=null;
private static Stack<Double> num = new Stack<>();
private static Stack<String> oper = new Stack<>();
private static Calculator cal=null;

private static void readAndTrans(String input){
        stringArray=input.split(" ");
}

private static double basicCal(){
        double sum=0;
        while(true) {
                if(oper.isEmpty()) { //if you pop empty stack member will get exception
                        if(num.isEmpty()) break;
                        else{
                                sum=sum+num.pop();
                                break;
                        }
                }
                String operator=oper.pop();
                if (operator.equals("-")) {
                        sum=sum-num.pop();
                }else if (operator.equals("+")) {
                        sum=sum+num.pop();
                }else if (operator.equals("(")) {
                        sum=sum+num.pop();
                        break;
                }else{
                        System.out.println("should not go here");
                        break;
                }
        }
        return sum;
}

private static double calAns(){
        //part1:translating database to only left double number and + -
        boolean multiED=false; //if multi number
        boolean divED=false; //if Div number
        for(int i=0; i<stringArray.length; i++) {
                if(divED) { //skip this index because already calculated
                        divED=false;
                }else if(multiED) {  //skip this index because already calculated
                        multiED=false;
                }else if (stringArray[i].equals(")")) {
                        num.push(basicCal());//calculate the value in bucket and push to num stack
                        if(oper.isEmpty()) {
                        }else{//before, if we multiple bucket we put * into stack and wait, now it's time to multi it
                                String temp=oper.pop();
                                if(temp.equals("/")) {
                                        double last=num.pop();
                                        double first=num.pop();
                                        num.push(first/last);
                                }else if(temp.equals("*")) {
                                        double last=num.pop();
                                        double first=num.pop();
                                        num.push(first*last);
                                }else oper.push(temp); //return the wrong oper which not * or /
                        }
                }else if(stringArray[i].equals("*")) {
                        if(!(stringArray[i+1].equals("("))) {
                                double temp=num.pop();
                                num.push(temp*Double.valueOf(stringArray[i+1]));
                                multiED=true;
                        }else{//stringArray[i+1]="("
                                oper.push(stringArray[i]);
                        }
                }else if (stringArray[i].equals("/")) {
                        if(!(stringArray[i+1].equals("("))) {
                                double temp=num.pop();
                                num.push(temp/Double.valueOf(stringArray[i+1]));
                                divED=true;
                        }else{//stringArray[i+1]="("
                                oper.push(stringArray[i]);
                        }
                }else if((stringArray[i].equals("+"))||(stringArray[i].equals("-"))||(stringArray[i].equals("("))) {
                        oper.push(stringArray[i]);
                }else{
                        num.push(Double.valueOf(stringArray[i]));
                }
                //System.out.println(cal.num.toString());
                //System.out.println(cal.oper.toString());
                //System.out.println("-------");
        }
        //part2:summing all double
        return basicCal();
}

public Double ans (String e) {
// please replace the following null to the answer you calculate.
        readAndTrans(e);
        return calAns();
}

public static void main(String[] args) {
        cal = new Calculator();
        System.out.println(cal.ans("1 + 2 * ( 3 + 3 * ( 2 * 3 ) * 2 ) / 2"));
}
}
