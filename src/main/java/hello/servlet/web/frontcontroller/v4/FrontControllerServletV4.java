package hello.servlet.web.frontcontroller.v4;

import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.MyView;
import hello.servlet.web.frontcontroller.v3.ControllerV3;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "frontControllerServletV4", urlPatterns = "/front-controller/v4/*")
public class FrontControllerServletV4 extends HttpServlet {

  private Map<String, ControllerV4> controllerMap = new HashMap<>();

  public FrontControllerServletV4() {
    controllerMap.put("/front-controller/v4/members/new-form", new MemberFormControllerV4());
    controllerMap.put("/front-controller/v4/members/save", new MemberSaveControllerV4());
    controllerMap.put("/front-controller/v4/members", new MemberListControllerV4());
  }

  @Override
  protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String requestURI = request.getRequestURI();

    ControllerV4 controller = controllerMap.get(requestURI);
    if (controller == null) {
      response.setStatus(HttpServletResponse.SC_NOT_FOUND);
      return;
    }

    Map<String, String> paramMap = createParamMap(request); // 파라미터를 다 뽑음
    Map<String, Object> model = new HashMap<>(); // 추가

    //ModelView mv = controller.process(paramMap); // 논리 이름 모델을 반환
    String viewName = controller.process(paramMap, model);

    //String viewName = mv.getViewName(); // 논리 이름 뷰 new-form
    MyView view = viewResolver(viewName); // 뷰리졸버가 MyView 반환
    //view.render(mv.getModel(), request, response); //렌더 호출하면서 모델을 넘김
    view.render(model, request, response);
  }

  private Map<String, String> createParamMap(HttpServletRequest request) {
    Map<String, String> paramMap = new HashMap<>();
    request.getParameterNames().asIterator()
        .forEachRemaining(paramName -> paramMap.put(paramName,
            request.getParameter(paramName)));
    return paramMap;
  }

  private MyView viewResolver(String viewName) {
    return new MyView("/WEB-INF/views/" + viewName + ".jsp");
  }

}
