/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package studybook;

/**
 *
 * @author Alex
 */
public class SBController {
    private SBModel model;
    private SBView view;

    public SBController(SBModel model) {
        this.model = model;
        view = new SBView(this);
        view.createMainFrame();
        view.layoutMainFrame();
    }
}
