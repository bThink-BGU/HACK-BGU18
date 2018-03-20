package flow.bgu.ac.il;

import javax.websocket.server.ServerEndpointConfig;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.websocket.jsr356.server.ServerContainer;
import org.eclipse.jetty.websocket.jsr356.server.deploy.WebSocketServerContainerInitializer;

public class BPServer {
	public static int PORT = 8090;


	/**
	 * Point your browser to the displayed URL
	 */
	public static void main(String[] args) throws Exception {
		Server server = new Server(PORT); 

		// Servlets

		ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
		context.setContextPath("/");
		server.setHandler(context);

		context.addServlet(new ServletHolder(new SaveServlet()), "/save");
		//context.addServlet(new ServletHolder(new ExportServlet()), "/export");
		context.addServlet(new ServletHolder(new FlowOpenServlet()), "/open");
		context.addServlet(new ServletHolder(new RunServlet()), "/run");

		ResourceHandler fileHandler = new ResourceHandler();
		fileHandler.setResourceBase(".");

		// Add javax.websocket support
        ServerContainer container = WebSocketServerContainerInitializer.configureContext(context);

        // Add endpoint to server container
        ServerEndpointConfig cfg = ServerEndpointConfig.Builder.create(EventQueue.class,"/eventqueue").build();
        container.addEndpoint(cfg);
		
		HandlerList handlers = new HandlerList();


		handlers.setHandlers(new Handler[] { fileHandler, context});
		server.setHandler(handlers);

		System.out.println(">> Go to http://localhost:" + PORT + "/editor/index.html");

		server.start();
		server.join();
	}
}
