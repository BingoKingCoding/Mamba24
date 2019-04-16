package ${moudlePackageName};


import dagger.Module;
import dagger.Provides;



@Module
public class ${pageName}Module {
    private ${pageName}Contract.View view;

    /**
     * 构建${pageName}Module时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     * @param view
     */
    public ${pageName}Module(${pageName}Contract.View view) {
        this.view = view;
    }

    @Provides
    ${pageName}Contract.View provide${pageName}View(){
        return this.view;
    }

}