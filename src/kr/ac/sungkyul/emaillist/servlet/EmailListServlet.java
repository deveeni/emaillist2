package kr.ac.sungkyul.emaillist.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.ac.sungkyul.emaillist.dao.EmailListDao;
import kr.ac.sungkyul.emaillist.vo.EmailListVo;


@WebServlet("/el")
public class EmailListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	request.setCharacterEncoding("utf-8");
	
	String actionName = request.getParameter( "a" );
	
	
	if( "list".equals( actionName )){
		//defaul action
		
		EmailListDao dao = new EmailListDao();
		List<EmailListVo> list = dao.getList();
		
		
		//request 범위(scope)에 객체를 저장
		request.setAttribute( "list" , list );
		
		//forwarding
		RequestDispatcher rd = request.getRequestDispatcher( "/WEB-INF/views/list.jsp" ); //분기시킴 request를 연장시킨다. request분기
		rd.forward(request, response ); //자기한테 있는 request를 넘겨준다. 
		//서버가 이동시키고 있고, 브라우저는 모른다. 
		//페이지 이동기술 : 포워딩

	}else if( "form".equals(actionName) ){
		//forwarding
		RequestDispatcher rd = request.getRequestDispatcher( "/WEB-INF/views/form.jsp" ); //분기시킴 request를 연장시킨다. request분기
		rd.forward(request, response ); //자기한테 있는 request를 넘겨준다. 
				
	}else if("insert".equals(actionName)){
		request.setCharacterEncoding("utf-8");
		String firstName = request.getParameter("fn");
		String lastName = request.getParameter("ln");
		String email = request.getParameter("email");
		
		EmailListVo vo = new EmailListVo();
		vo.setFirstName(firstName);
		vo.setLastName(lastName);
		vo.setEmail(email);
		
		EmailListDao dao = new EmailListDao();
		boolean result = dao.insert(vo);
		
		response.sendRedirect("/emaillist2/el");
	
	
	
	}else{
		//주소창에 뭐라고 쳐도 list.jsp가 나오게 된다.
		
		EmailListDao dao = new EmailListDao();
		List<EmailListVo> list = dao.getList();
		
		
		//request 범위(scope)에 객체를 저장
		request.setAttribute( "list" , list );
		
		//forwarding
		RequestDispatcher rd = request.getRequestDispatcher( "/WEB-INF/views/list.jsp" ); //분기시킴 request를 연장시킨다. request분기
		rd.forward(request, response ); //자기한테 있는 request를 넘겨준다. 
		
	}
	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet( request, response );//한곳에서 모든것을 처리~
	
	
	}

}
