package xin.wangning;

import com.alibaba.fastjson.JSONObject;
import xin.wangning.domain.User;

import java.io.*;

public class GccServlet extends javax.servlet.http.HttpServlet {
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        System.out.println("get it");
        response.setCharacterEncoding("utf-8");
        String code = request.getParameter("code");
        if(code==null||code.equals("")){
            PrintWriter pw = response.getWriter();
            pw.write("code error");
            pw.flush();
            pw.close();
        }
        //唯一的头文件
        String header = request.getParameter("LinkList.h");
        if(header==null||header.equals("")){
            PrintWriter pw = response.getWriter();
            pw.write("code error");
            pw.flush();
            pw.close();
        }
        User user = (User) request.getSession().getAttribute("user");
        String path = getServletContext().getRealPath("")+user.getUsername();
        File file = new File(path);
        if(file.exists()){
            file.delete();
        }
        file.mkdirs();
        path = getServletContext().getRealPath("")+user.getUsername()+File.separator;
        System.out.println("path:"+path);
        File file1 = new File(path+"code.cpp");
        if(file1.exists()){
            file1.delete();
        }
        file1.createNewFile();
        FileWriter fw = new FileWriter(file1);
        fw.write(code.replace("\240"," "));
        fw.flush();
        fw.close();
        File file2 = new File(path+"LinkList.h");
        if(file2.exists()){
            file2.delete();
        }
        file2.createNewFile();
        fw = new FileWriter(file2);
        fw.write(header.replace("\240"," "));
        fw.flush();
        fw.close();
        String shell = "g++ "+file1.getAbsoluteFile()+" -o "+path+"code";
        System.out.println(shell);
        RunResult comResult = compile(shell,path);
        System.out.println("comResult:"+comResult+(comResult.getResult().equals("")));
        PrintWriter pw = response.getWriter();
        if(comResult.getResult().equals("")){
            String run = path+"code";
            String result = run(run,path,user);
            System.out.println("run: "+ result);
            pw.write(result);
        }else {
            pw.write(JSONObject.toJSONString(comResult));
        }
        pw.flush();
        pw.close();
    }

    public RunResult compile(String shell,String toReplace) throws IOException {
        Runtime runtime = Runtime.getRuntime();
        Process process = runtime.exec(shell);
        BufferedReader errorInput = new BufferedReader(new InputStreamReader(process.getErrorStream()));
        StringBuilder builder = new StringBuilder();
        //好吧，包含
        RunResult runResult = new RunResult();
        String line = "";
        while((line=errorInput.readLine())!=null){
            builder.append(line+"\n");
        }
        runResult.setResult(builder.toString().replace(toReplace,""));
        return runResult;
    }

    public String run(String shell,String path,User user) throws IOException {
        Runtime runtime = Runtime.getRuntime();
        Process process = runtime.exec(shell);
        BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
        RunResult runResult = new RunResult();
        //产生图片数
        int picNum = 0;
        //设置防止死循环
        int linenum = 0;
        //新建计时线程，防止死循环
        final boolean[] endlessLoop = {false};
        Thread thread = new Thread(){
            public void run(){
                try {
                    sleep(5000);
                    System.out.println("endSleep");
                    try {
                        process.destroy();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    endlessLoop[0] =true;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        };
        thread.start();
        StringBuilder builder = new StringBuilder();
        String line = "";
        while((line=input.readLine())!=null){
            linenum++;
            if(linenum>=150){
                builder.append("此处省略若干，请检查是否死循环或者减少输出！！！");
                break;
            }
            if(line.contains("dot代码")){
                picNum++;
                creatPNG(path,line.replace("dot代码",""),user,"showlinklist"+String.valueOf(picNum)+".png");
            }else {
                builder.append(line+"\n");
            }
            System.out.println("line:"+line);
        }
        if(endlessLoop[0]){
            builder.append("超时，请检查是否死循环！！！");
        }
        runResult.setResult(builder.toString());
        runResult.setDirectoryName("/"+user.getUsername()+"/");
        runResult.setPicNum(picNum);
        return JSONObject.toJSONString(runResult);
    }

    //文件路径以及dot代码
    public void creatPNG(String path,String code,User user,String pictureName)throws IOException{
        File file = new File(path+pictureName+".dot");
        if(file.exists()){
            file.delete();
        }
        file.createNewFile();
        FileWriter writer = new FileWriter(file.getAbsolutePath());
        writer.write(code);
        writer.flush();
        writer.close();
        Runtime runtime = Runtime.getRuntime();
        Process process = runtime.exec("dot -Tpng "+file.getAbsolutePath()+" -o "+path+pictureName);
    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        doPost(request, response);
    }
    class RunResult{
        String result;
        //带分割
        String directoryName;
        //图片名形式一致
        int picNum;

        public String getResult() {
            return result;
        }

        public void setResult(String result) {
            this.result = result;
        }

        public String getDirectoryName() {
            return directoryName;
        }

        public void setDirectoryName(String directoryName) {
            this.directoryName = directoryName;
        }

        public int getPicNum() {
            return picNum;
        }

        public void setPicNum(int picNum) {
            this.picNum = picNum;
        }
    }
}
