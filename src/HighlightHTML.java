import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.Stack;

/**
 * Created by aman on 2/14/17.
 */
public class HighlightHTML {
    String previousColor = "";
//    String previousTag = "";
//    Stack tags = new Stack();


    /**
     * Function to separate braces and return only the tag name
     * @param tag tag can be a starting/ending tag and can contain multiple attributes
     * @return tagname as String
     */
    String getTagName(String tag){
        int length = tag.length();
        String test = "";
        if(tag.length() > 2 && tag.substring(0,1).equals("<")){
            if (tag.substring(1,2).equals("/")) {
                test = tag.substring(2,length-1);
            }
            else{
                test = tag.substring(1, length-1);
            }
        }
        else{
            test= tag;
        }
        test = test.toLowerCase();
        String testArr [] = test.split("\\s+");
        test = testArr[0];
        return test;
    }


    /**
     * Method to return colorCode depending on the tag provided
     * @param tag can be html, body, a, p , title, head, br or h1
     * @return the colorCode as string
     */
    String getColor(String tag){
        String test = this.getTagName(tag);
        switch (test){
            case "html":{
                return "\\color[RED]";
            }
            case "head":{
                return "\\color[YELLOW]";
            }
            case "title":{
                return "\\color[GREEN]";
            }
            case "body":{
                return "\\color[TURQUOISE]";
            }
            case "br":{
                return "\\color[PINK]";
            }
            case "p":{
                return "\\color[DARKGRAY]";
            }
            case "h1":{
                return "\\color[DARKGREEN]";
            }
            case "a":{
                return "\\color[BLUE]";
            }
        }
        return "\\color[TURQUOISE]";
    }

    /**
     * Overloaded method addColorCode used when there is no tag in line
     * @param inputString a line without any tags
     * @return a line with color code TURQUOISE if the previous line ended with a different color
     */
    String addColorCode(String inputString){
        String color = this.getColor("");
        if (this.previousColor.equals(color)){
            return inputString;
        }
        else {
            this.previousColor = color;
            return color + inputString;
        }
    }

    /**
     * This method simply reads the input string and adds the colorCode at the appropriate position
     * @param inputString The entire input string
     * @param start The index of the beginning position of a tag
     * @param end The index of the end position of a tag
     * @return The substring of the input string until the '<' of the tag
     */
    String addColorCode(String inputString, int start, int end){
        String tag = inputString.substring(start, start + end + 1);
        String color = this.getColor(tag);
        if (this.previousColor.equals(color)){
            return inputString.substring(0, start) +"<";
        }
        else {
            this.previousColor = color;
            return inputString.substring(0, start) + color + "<";
        }
    }

    /**
     * This method is used to add color to each line read through the buffered reader.
     * The method looks for an instance of '<' and reads the tag until '>'.
     * The method then calls the addColorCode method on the tag
     * @param inputString Current line in the buffered reader
     * @return Line with the correct color codes inserted at the beginning and at the end of the tag.
     */
    String readTagsFromString(String inputString){
        boolean testCheck = false;
        int initLength = inputString.length();
        for (int i=0;i < inputString.length();i++)
        {
            if (inputString.substring(i,i+1).equals("<")) {
                if(inputString.substring(i+1,i+2).equals("/")){
                    if(testCheck){

                        testCheck = false;
                        continue;
                    }
                }
                else{
                    testCheck = true;
                }
                int j = 0;
                while (!inputString.substring(i + j, i + j + 1).equals(">")) {
                    j++;
                }
                String addedColorString = this.addColorCode(inputString, i , j);
                inputString =  addedColorString+inputString.substring(i + 1, inputString.length());
                i = addedColorString.length()+1;
            }
        }
        if(initLength == inputString.length() && initLength >0){
            inputString = this.addColorCode(inputString);
        }
        return inputString;
    }

//    String parseCompleteHTML(String inputString){
//        for (int i=0;i < inputString.length();i++)
//        {
//            if (inputString.substring(i,i+1).equals("<")) {
//                int j = 0;
//                while (!inputString.substring(i + j, i + j + 1).equals(">")) {
//                    j++;
//                }
//                String tagComplete = inputString.substring(i , i+j+1);
//                String tag = this.getTagName(tagComplete);
//                String color = "";
//                if(tagComplete.substring(1,2).equals("/")) {
//                    if(this.previousTag.equals(tag)){
//                        this.previousTag = this.tags.pop().toString();
//                        System.out.println("End S"+tagComplete);
//                        System.out.println(this.tags.toString());
//                        continue;
//                    }else{
//                        color = this.getColor(this.tags.pop().toString());
//                        inputString = inputString.substring(0,i)+color+inputString.substring(i+1);
//                        System.out.println("End"+color+tagComplete);
//                    }
//                }
//                else{
//                    color = this.getColor(tagComplete);
//                    inputString = inputString.substring(0,i)+color+inputString.substring(i+1);
//                    System.out.println("Start"+color+tagComplete);
//                    this.previousTag = tag;
//                    if(tag.equals("br") || tag.equals("hr"))
//                        continue;
//                    this.tags.push(tag);
//                }
//                i = inputString.substring(0,1).length()+color.length()+1;
//            }
//        }
//        return inputString;
//    }

    public static void main(String args[]){
        BufferedReader br;
        HighlightHTML one = new HighlightHTML();
        if(args.length < 1){
            System.out.println("Please enter valid HTML string: ");
            br = new BufferedReader(new InputStreamReader(System.in));
        }
        else{
            String fileName = args[0];
            FileReader fr = null;
            try {
                fr = new FileReader(fileName);
            }
            catch(Exception e){
                e.printStackTrace();
            }
            br = new BufferedReader(fr);
            }
        try {
            String inputCompString = "";
            String outputString = "";
            String s = br.readLine();
            while(s != null){
                inputCompString += s;
                outputString += one.readTagsFromString(s) + "\n";
                if(s.toLowerCase().contains("</html>")){
                    break;
                }
                s = br.readLine();
            }
            System.out.println(outputString);
//            System.out.println(one.parseCompleteHTML(inputCompString));
            return;
        }catch (Exception e){
            e.printStackTrace();
        }
        return;
    }

}
