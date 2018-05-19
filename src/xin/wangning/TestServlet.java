package xin.wangning;

import java.io.*;

public class TestServlet extends javax.servlet.http.HttpServlet {
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        System.out.println("get it");
        String code = request.getParameter("code");
        if(code==null||code.equals("")){
            PrintWriter pw = response.getWriter();
            pw.write("code error");
            pw.flush();
            pw.close();
        }
        String path = getServletContext().getRealPath("");
        File file = new File(path+"code.c");
        if(file.exists()){
            file.delete();
        }
        file.createNewFile();
        FileWriter fw = new FileWriter(file);
        System.out.println("code : "+code);
        fw.write(code);
        fw.flush();
        fw.close();
        String shell = "gcc "+file.getAbsoluteFile()+" -o "+path+"code";
        System.out.println(shell);
        String comResult = compile(shell);
        System.out.println("comResult:"+comResult+(comResult.equals("")));
        PrintWriter pw = response.getWriter();
        if(comResult.equals("")){
            String run = path+"code";
            String result = run(run);
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

    public String run(String shell) throws IOException {
        Runtime runtime = Runtime.getRuntime();
        Process process = runtime.exec(shell);
        BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
        StringBuilder builder = new StringBuilder();
        String line = "";
        while((line=input.readLine())!=null){
            builder.append(line);
        }
        return builder.toString();
    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        doPost(request, response);
    }
}
