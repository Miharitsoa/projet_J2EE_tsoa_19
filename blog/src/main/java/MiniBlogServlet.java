

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.io.*;
/**
 * Servlet implementation class MiniBlogServlet
 */
@WebServlet("/blog")
public class MiniBlogServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MiniBlogServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    
    private List<String> articles = new ArrayList<>();

    @Override
    public void init() throws ServletException {
     /*   articles.add(new java.util.Date().toString()+"- Premier article");
        articles.add(new java.util.Date().toString()+"- Deuxième article");
        articles.add(new java.util.Date().toString()+"- Troisième article");
    */}

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html><head><title>Mini Blog</title></head><body>");
        out.println("<h1>Mini Blog</h1>");
        out.println("<h2>Conservez vos notes</h2>");
        out.println("<ul>");
        for (int i = 0; i < articles.size(); i++) {
            String article = articles.get(i);
            out.println("<li>" + article + " <form method=\"post\" action=\"MiniBlogServlet\">"
                        + "<input type=\"hidden\" name=\"action\" value=\"delete\">"
                        + "<input type=\"hidden\" name=\"index\" value=\"" + i + "\">"
                        + "<button type=\"submit\">Supprimer</button>"
                        + "</form></li>");
        }
        out.println("</ul>");
        out.println("<form action=\"MiniBlogServlet\" method=\"post\">");
        out.println("<label for=\"article\">Nouvel article:</label>");
        out.println("<input type=\"text\" id=\"article\" name=\"article\">");
        out.println("<button type=\"submit\">Ajouter</button>");
        out.println("</form>");
        out.println("</body></html>");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String newArticleContent = request.getParameter("article");
        java.util.Date currentDate = new java.util.Date();
        String dateTime = currentDate.toString();
        String newArticle = dateTime + " - " + newArticleContent;
        articles.add(newArticle);
        saveArticlesToFile();
        String action = request.getParameter("action");
        if("delete".equals(action)) {
        	String indexString = request.getParameter("index");
        	if(indexString != null && !indexString.isEmpty()) {
        		int index = Integer.parseInt(indexString);
        		if(index >=0 && index < articles.size()) {
        			articles.remove(index);
        			saveArticlesToFile();
        		}
        	}
        }
        response.sendRedirect(request.getContextPath() + "/MiniBlogServlet");
    }
    
    private void saveArticlesToFile() {
    	String fileName = "/home/tsoa/eclipse-workspace/blog/src/main/webapp/article.txt";
    	try(BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))){
    		for(String article : articles) {
    			writer.append(article);
    			writer.newLine();
    		}
    	} catch(IOException e) {
    		e.printStackTrace();
    	}
    }

}
