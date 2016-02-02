package cheetah.domain;

/**
 * Created by Max on 2016/1/18.
 */
public class AssemblyManager {
    private Assembly assembly;
    private AssemblyManager(){}

    private final static AssemblyManager assemblyManager = new AssemblyManager();

    public static AssemblyManager getManager() {
        return assemblyManager;
    }

    public static Assembly getJpaAssembly() {
        return null;
    }
}
