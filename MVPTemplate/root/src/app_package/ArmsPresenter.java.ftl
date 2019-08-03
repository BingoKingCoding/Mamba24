package ${presenterPackageName};

import javax.inject.Inject;



public class ${pageName}Presenter extends BasePresenter<${pageName}Contract.View> implements ${pageName}Contract.Presenter{
 

    @Inject
    public ${pageName}Presenter (IRepository repository) {
        super(repository);
    }


}
