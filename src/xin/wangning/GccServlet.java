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
        fw.write(code);
        fw.flush();
        fw.close();
        File file2 = new File(path+"LinkList.h");
        if(file2.exists()){
            file2.delete();
        }
        file2.createNewFile();
        fw = new FileWriter(file2);
        fw.write(header);
        fw.flush();
        fw.close();
        String shell = "g++ "+file1.getAbsoluteFile()+" -o "+path+"code";
        System.out.println(shell);
        String comResult = compile(shell);
        System.out.println("comResult:"+comResult+(comResult.equals("")));
        PrintWriter pw = response.getWriter();
        if(comResult.equals("")){
            String run = path+"code";
            String result = run(run,path,user);
            System.out.println("run: "+ result);
            pw.write(result);
        }else {
            pw.write(comResult);
        }
        pw.flush();
        pw.close();
    }

    public String compile(String shell) throws IOException {
        Runtime runtime = Runtime.getRuntime();
        Process process = runtime.exec(shell);
        BufferedReader errorInput = new BufferedReader(new InputStreamReader(process.getErrorStream()));
        StringBuilder builder = new StringBuilder();
        String line = "";
        while((line=errorInput.readLine())!=null){
            builder.append(line);
        }
        return builder.toString();
    }

    public String run(String shell,String path,User user) throws IOException {
        Runtime runtime = Runtime.getRuntime();
        Process process = runtime.exec(shell);
        BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
        RunResult runResult = new RunResult();
        StringBuilder builder = new StringBuilder();
        String line = "";
        while((line=input.readLine())!=null){
            if(line.contains("dot代码")){
                creatPNG(path,line.replace("dot代码",""),user);
            }else {
                builder.append(line+"\n");
            }
        }
        runResult.setResult(builder.toString());
        runResult.setImage("/"+user.getUsername()+"/showlinklist.png");
        return JSONObject.toJSONString(runResult);
    }

    //文件路径以及dot代码
    public void creatPNG(String path,String code,User user)throws IOException{
        File file = new File(path+"showlinklist.dot");
        if(file.exists()){
            file.delete();
        }
        file.createNewFile();
        FileWriter writer = new FileWriter(file.getAbsolutePath());
        writer.write(code);
        writer.flush();
        writer.close();
        Runtime runtime = Runtime.getRuntime();
        Process process = runtime.exec("dot -Tpng "+file.getAbsolutePath()+" -o "+path+"showlinklist.png");
    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        doPost(request, response);
    }
    class RunResult{
        String result;
        String image;

        public String getResult() {
            return result;
        }

        public void setResult(String result) {
            this.result = result;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }
    }
}
