package chat.servlets;

import engine.Engine;
import chat.utilities.ServletUtils;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import engine.login.users.PermissionManager;
import engine.login.users.UserManager;

public class AppContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
//        SheetManager userSheetManager = new SheetManagerImpl();  // Initialize the engine
//        context.setAttribute("engine", userSheetManager);  // Store in ServletContext
        Engine engine = new Engine();  // Initialize the engine
        context.setAttribute(ServletUtils.ENGINE_MANAGER_ATTRIBUTE_NAME, engine);  // Store in ServletContext

        UserManager userManager = new UserManager();
        context.setAttribute(ServletUtils.USER_MANAGER_ATTRIBUTE_NAME, userManager);

        PermissionManager permissionManager = new PermissionManager(userManager);
        context.setAttribute(ServletUtils.PERMISSION_MANAGER_ATTRIBUTE_NAME, permissionManager);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // Clean up resources here if needed
    }
}
