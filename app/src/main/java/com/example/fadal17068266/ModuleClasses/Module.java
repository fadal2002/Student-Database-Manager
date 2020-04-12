package com.example.fadal17068266.ModuleClasses;

public class Module {
    private int modId;
    private String moduleName;
    private String moduleDescription;

    public Module(int modId, String moduleName, String moduleDescription) {
        this.modId = modId;
        this.moduleName = moduleName;
        this.moduleDescription = moduleDescription;
    }

    public Module(String moduleName, String moduleDescription) {
        this.moduleName = moduleName;
        this.moduleDescription = moduleDescription;
    }

    public int getModId() {
        return modId;
    }

    public void setModId(int modId) {
        this.modId = modId;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getModuleDescription() {
        return moduleDescription;
    }

    public void setModuleDescription(String moduleDescription) {
        this.moduleDescription = moduleDescription;
    }
}
