package ${presenterPackageName};

import ${applicationPackage}.app.http.IRepository;
import ${applicationPackage}.app.base.BasePresenter;
import javax.inject.Inject;



public class ${pageName}Presenter extends BasePresenter<${pageName}Contract.View> {
 

    @Inject
    public ${pageName}Presenter (IRepository repository) {
        super(repository);
    }


}
