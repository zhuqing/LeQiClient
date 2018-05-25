package com.leqienglish.client.control.date.pagebar;




import com.leqienglish.client.control.skin.LQSkin;
import com.leqienglish.client.util.local.LocalUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import jidefx.scene.control.decoration.DecorationPane;
import jidefx.scene.control.decoration.DecorationUtils;
import jidefx.scene.control.decoration.Decorator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 分页控制条皮肤类
 *
 * @author duyi
 * @version 1.0 26-三月-2014 10:17:55
 */
public class NavigationPageBarSkin extends LQSkin<NavigationPageBar, NavigationPageBarBehavior> {

    private BorderPane rootPn;
    /**
     * 显示最大页码 的显示条
     */
    private HBox pageBox;
    /**
     * 所有显示按钮和 页码
     */
    private HBox centerBox;
    /**
     * 下一页按钮
     */
    private ToggleButton nextBtn;
    /**
     * 上一页按钮
     */
    private ToggleButton preBtn;
    /**
     * 首页按钮
     */
    private ToggleButton firstBtn;
    /**
     * 末页按钮
     */
    private ToggleButton lastBtn;
    /**
     * 更多页按钮
     */
    private ToggleButton moreBtn;
    /**
     * 带页码的按钮
     */
    private List<ToggleButton> showToggleButtonList;
    /**
     * 所有按钮的组
     */
    private ToggleGroup toggleButtons;

    /**
     * 首页按钮的Id
     */
    private final static String FIRST_PAGE_BUTTON_ID = "page_first_btn";
    /**
     * 末页按钮的Id
     */
    private final static String LAST_PAGE_BUTTON_ID = "page_last_btn";
    /**
     * 上一页按钮的Id
     */
    private final static String PRE_PAGE_BUTTON_ID = "page_pre_btn";
    /**
     * 下一页按钮的Id
     */
    private final static String NEXT_PAGE_BUTTON_ID = "page_next_btn";
    /**
     * 更多页按钮的Id
     */
    private final static String MORE_PAGE_BUTTON_ID = "page_more_btn";
    /**
     * 带回车图片的输入框的内输入框Id
     */
    private final static String INPUT_PAGENUM_ID = "page_num_input";
    /**
     * 带回车图片的输入框的Id
     */
    private final static String INPUT_IMAGE_ID = "page_num_enter";
    /**
     * 显示总页码的Id
     */
    private final static String TOTAL_PAGE_BUTTON_ID = "page_num_total";

    /**
     * button的样式
     */
    private final static String TOGGLE_BUTTON_STYPE = "toggle-button-pagebar";

    /**
     * 总页码数
     */
    private Label pageTotalLabel;
    /**
     * 总页码数 前的 共
     */
    private Label perPageTotalLabel;
    /**
     * 总页码数 后的 页
     */
    private Label nextPageTotalLabel;
    /**
     * 带回车图片的输入框
     */
    private EnterTextFiled inputPage;
    /**
     * 带回车图片的输入框 的默认内容
     */
    private final static String INPUT_STRING = "页码";
    /**
     * 显示的起始页码
     */
    private int startPage;
    /**
     * 显示的结束页码
     */
    private int endPage;
    /**
     * 监听最大页码修改事件
     */
    private final static String MAX_PAGE = "max_page";
    /**
     * 监听当前页码修改事件
     */
    private final static String NOW_PAGE = "now_page";
    /**
     * 前一个最大页码
     */
    private int oldMaxPage = 0;

    private static final Logger LOGGER = LoggerFactory.getLogger(NavigationPageBarSkin.class);

    public NavigationPageBarSkin(NavigationPageBar pageBar) {
        super(pageBar, new NavigationPageBarBehavior(pageBar));
    }

    @Override
    public void initSkin() {
        this.registerChangeListener(getSkinnable().maxPageProperty(), MAX_PAGE);
        this.registerChangeListener(getSkinnable().nowPageProperty(), NOW_PAGE);
        this.oldMaxPage = this.getSkinnable().getMaxPage();
        this.rootPn = new BorderPane();
        //this.rootPn.d
        this.createPageBarInnerNode();
        this.createCenterBox();
        this.addNode();
        initKeyEvent();
    }

    private void initKeyEvent() {
//        KeyEventHandlerUtil.regist(this.getSkinnable(), new Consumer<KeyEvent>() {
//            @Override
//            public void accept(KeyEvent t) {
//                int nowPage = getSkinnable().getNowPage();
//                if (nowPage > 1) {
//                    nowPage -= 1;
//                }
//                getSkinnable().setNowPage(nowPage);
//            }
//        }, KeyCode.PAGE_UP);
//
//        KeyEventHandlerUtil.regist(this.getSkinnable(), new Consumer<KeyEvent>() {
//            @Override
//            public void accept(KeyEvent t) {
//                int nowPage = getSkinnable().getNowPage();
//                if (nowPage < getSkinnable().getMaxPage()) {
//                    nowPage += 1;
//                }
//                getSkinnable().setNowPage(nowPage);
//            }
//        }, KeyCode.PAGE_DOWN);

    }


    @Override
    public void showSkin() {
        this.rootPn.setCenter(this.centerBox);
        this.getChildren().add(this.rootPn);
    }

    @Override
    public void handleControlPropertyChanged(String type) {
        LOGGER.debug("handleControlPropertyChanged =" + type + ":" + this.getSkinnable().getMaxPage() + ":" + this.getSkinnable().getNowPage());
        if (MAX_PAGE.equals(type)) {
            if (this.oldMaxPage <= this.getSkinnable().getShowPageBtn() || this.getSkinnable().getMaxPage() <= this.getSkinnable().getShowPageBtn()) {
                this.addNode();
            }
            this.changePageNumber();
            this.oldMaxPage = this.getSkinnable().getMaxPage();
            this.modifyInputPageAndPageBox();
        } else if (NOW_PAGE.equals(type)) {
            this.changePageNumber();
        }
//        switch (type) {
//            case MAX_PAGE:
//                // LOGGER.debug("handleControlPropertyChanged =" + type+":"+this.getSkinnable().getMaxPage());
//                if (this.oldMaxPage <= this.getSkinnable().getShowPageBtn() || this.getSkinnable().getMaxPage() <= this.getSkinnable().getShowPageBtn()) {
//                    this.addNode();
//                }
//                this.changePageNumber();
//                this.oldMaxPage = this.getSkinnable().getMaxPage();
//                this.modifyInputPageAndPageBox();
//                break;
//            case NOW_PAGE:
//                this.changePageNumber();
//                break;
//            default:
//                
//                break;
//        }
    }

    /**
     * 添加按钮到界面
     */
    private void addNode() {
        this.centerBox.getChildren().clear();

        if (this.getSkinnable().getMaxPage() > this.getSkinnable().getShowPageBtn()) {
            this.addNode(this.firstBtn);
            this.addNode(this.preBtn);

        }
        this.addToggleButtonToCenterHbox();
        if (this.getSkinnable().getMaxPage() > this.getSkinnable().getShowPageBtn()) {
            this.addNode(this.nextBtn);
            this.addNode(this.lastBtn);
        }

        this.modifyInputPageAndPageBox();
    }

    /**
     * 初始化 pageButton
     */
    private void createPageBarInnerNode() {
        try {
            if (this.getSkinnable().isIsShowFirstAndLastBtn()) {
//                this.firstBtn = this.createToggleButton(FIRST_PAGE_BUTTON_ID, "page_first_btn", LocalUtil.getLiteral("firstPage"));
                this.firstBtn = this.createToggleButton(FIRST_PAGE_BUTTON_ID, "page_first_btn", LocalUtil.getLiteral("首页"));
                this.lastBtn = this.createToggleButton(LAST_PAGE_BUTTON_ID, "page_last_btn", LocalUtil.getLiteral("尾页"));
            }
            if (this.getSkinnable().isIsShowPreAndNextBtn()) {
                this.preBtn = this.createToggleButton(PRE_PAGE_BUTTON_ID, "page_pre_btn", LocalUtil.getLiteral("上一页"));
                this.nextBtn = this.createToggleButton(NEXT_PAGE_BUTTON_ID, "page_next_btn", LocalUtil.getLiteral("下一页"));
            }
            if (this.getSkinnable().getShowPageBtn() > 1) {
                this.moreBtn = this.createToggleButton(MORE_PAGE_BUTTON_ID, "TOGGLE_BUTTON_STYPE", "...");
            }
            this.createPageNumToggleButton();
            if (this.getSkinnable().isIsShowInput()) {
                this.createInputPage();
            }
            if (this.getSkinnable().isIsShowTotalLabel()) {
                this.createPageBox();
            }
        } catch (IOException ex) {
            LOGGER.debug(null, ex);

        }
    }

    /**
     * 创建togglebutton
     *
     * @param buttonId
     * @param css
     * @param showText
     * @return
     */
    private ToggleButton createToggleButton(String buttonId, String css, String showText) {
        ToggleButton toggleButton = new ToggleButton();
        toggleButton.setId(buttonId);
//        toggleButton.getStyleClass().removeAll();
        toggleButton.getStyleClass().add(css);
        if (showText != null) {
            toggleButton.setText(showText);
        }
        toggleButton.setOnMouseClicked(new ButtonClickEventHandler());
        toggleButton.setToggleGroup(this.toggleButtons);
        return toggleButton;
    }

    /**
     * 创建togglebutton
     *
     * @param buttonId
     * @param css
     * @return
     */
    private ToggleButton createToggleButton(String buttonId, String css) {
        return createToggleButton(buttonId, css, null);
    }

    /**
     * 创建CenterBox
     */
    private void createCenterBox() {
        this.centerBox = new HBox();
        this.centerBox.getStyleClass().add("hbox-pagebar");
        this.centerBox.setAlignment(Pos.CENTER);

    }

    /**
     * createPageBox
     */
    private void createPageBox() {
        this.pageBox = new HBox();
        this.pageBox.getStyleClass().add("hbox-page");
        this.pageBox.setAlignment(Pos.CENTER);

        this.pageTotalLabel = this.createLable(TOTAL_PAGE_BUTTON_ID, "lable-pagenumber", this.getSkinnable().getMaxPage() + "");
        this.perPageTotalLabel = this.createLable(TOTAL_PAGE_BUTTON_ID + "pre", "lable-page", "共");
        this.nextPageTotalLabel = this.createLable(TOTAL_PAGE_BUTTON_ID + "next", "lable-page", "页");

        this.pageBox.getChildren().add(this.perPageTotalLabel);
        this.pageBox.getChildren().add(this.pageTotalLabel);
        this.pageBox.getChildren().add(this.nextPageTotalLabel);
    }

    /**
     * 创建Label
     *
     * @param css
     * @param showText
     * @return
     */
    private Label createLable(String id, String css, String showText) {
        Label label = new Label(showText);
        label.setId(id);
        label.getStyleClass().add(css);
        return label;
    }

    /**
     * 根据根据最大页码修改是否显示信息
     */
    private void modifyInputPageAndPageBox() {

        if (this.getSkinnable().getMaxPage() > this.getSkinnable().getShowPageBtn()) {
            if (this.inputPage != null) {
                this.inputPage.setFocusTraversable(true);
            }
            this.addNode(this.inputPage);

            this.addNode(this.pageBox);
            if (this.pageTotalLabel != null) {
                pageTotalLabel.setText(this.getSkinnable().getMaxPage() + "");
            }
        } else {
            this.removeNode(this.inputPage);

            this.removeNode(this.pageBox);
        }
    }

    /**
     * 删除节点
     *
     * @param node
     */
    private void removeNode(Node node) {
        if (node == null) {
            return;
        }
        if (this.centerBox.getChildren().contains(node)) {
            this.centerBox.getChildren().remove(node);
        }
    }

    /**
     * 添加节点
     *
     * @param node
     */
    private void addNode(Node node) {
        if (node == null) {
            return;
        }
        if (!this.centerBox.getChildren().contains(node)) {
            this.centerBox.getChildren().add(node);
        }
    }

    /**
     * 初始化输入框
     */
    private void createInputPage() {
        if (this.inputPage == null) {
            this.inputPage = new EnterTextFiled(INPUT_STRING);
            this.inputPage.setId(INPUT_PAGENUM_ID);
            this.inputPage.setMaxHeight(27);
            this.inputPage.setMaxWidth(60);
            this.inputPage.setMinHeight(27);
            this.inputPage.setMinWidth(60);
        }

    }

    /**
     * 设置disable的button
     */
    private void disableButton() {

        this.setDisable(firstBtn, false);
        this.setDisable(preBtn, false);
        this.setDisable(lastBtn, false);
        this.setDisable(nextBtn, false);
        if (this.getSkinnable().getNowPage() == 1) {
            this.setDisable(firstBtn, true);
            this.setDisable(preBtn, true);
        }

        if (this.getSkinnable().getNowPage() == this.getSkinnable().getMaxPage()) {
            this.setDisable(lastBtn, true);
            this.setDisable(nextBtn, true);
        }
        this.setSelected(firstBtn, false);
        this.setSelected(preBtn, false);
        this.setSelected(lastBtn, false);
        this.setSelected(nextBtn, false);
    }

    /**
     * setDisable
     *
     * @param toggleButton
     * @param isDis
     */
    private void setDisable(ToggleButton toggleButton, boolean isDis) {
        if (toggleButton == null) {
            return;
        }
        toggleButton.setDisable(isDis);
    }

    /**
     * setSelected
     *
     * @param toggleButton
     * @param isSelected
     */
    private void setSelected(ToggleButton toggleButton, boolean isSelected) {
        if (toggleButton == null) {
            return;
        }
        toggleButton.setSelected(isSelected);
    }

    /**
     * 创建页码toggleButton
     */
    private void createPageNumToggleButton() {
        this.toggleButtons = new ToggleGroup();

        this.showToggleButtonList = new ArrayList<ToggleButton>();
        for (int i = 0; i < this.getSkinnable().getShowPageBtn(); i++) {
            ToggleButton btn = this.createToggleButton("page_num" + (i + 1) + "_btn", TOGGLE_BUTTON_STYPE);
            this.showToggleButtonList.add(btn);
        }

    }

    /**
     * 将toggleButton添加到界面
     */
    private void addToggleButtonToCenterHbox() {
        if (this.getSkinnable().getShowPageBtn() <= 1 || this.getSkinnable().getMaxPage() == 1) {
            return;
        }
        this.changePageNumber();
//        this.centerBox.getStyleClass().add("hbox");
        for (int i = 0; i < this.getSkinnable().getMaxPage() & i < this.showToggleButtonList.size(); i++) {
            this.centerBox.getChildren().add(this.showToggleButtonList.get(i));
        }

        if (this.getSkinnable().getMaxPage() > this.getSkinnable().getShowPageBtn()) {
            this.centerBox.getChildren().add(this.moreBtn);
        }
    }

    /**
     * *
     * 修改显示页码
     */
    public void changePageNumber() {
        LOGGER.debug(this.getStartPage() + ":" + this.getEndPage() + ":" + this.getSkinnable().getNowPage() + ":" + this.getSkinnable().getMaxPage());
        if (toggleButtons.getSelectedToggle() != null) {
            toggleButtons.getSelectedToggle().setSelected(false);
        }

        this.countStartAndEndPage(this.getSkinnable().getNowPage());
        this.changePageButton();
        this.disableButton();
    }

    /**
     * 根据输入内容改变当前页
     *
     * @param pageNum
     */
    private void changeNowPage(String pageNum) {
        LOGGER.debug("changeNowPage==>" + pageNum);
        try {
            int page = Integer.parseInt(pageNum);
            if (page > this.getSkinnable().getMaxPage()) {
                page = this.getSkinnable().getMaxPage();
                this.inputPage.setText(page + "");
            }
            this.getSkinnable().setNowPage(page);
        } catch (Exception e) {
            LOGGER.error("changeNowPage==>" + pageNum + "\n" + e.getMessage());
        }
    }

    /**
     * 计算显示的第一页码数 和最后一个页码数
     *
     * @return
     */
    private void countStartAndEndPage(int page) {
        if (this.getSkinnable().getShowPageBtn() <= 1) {
            this.startPage = this.endPage = page;
            return;
        }
        int maxPage = this.getSkinnable().getMaxPage();
        if (maxPage <= this.getSkinnable().getShowPageBtn()) {
            this.startPage = 1;
            this.endPage = maxPage;
            return;
        }

        int nowPage = page;
        this.startPage = nowPage - this.getSkinnable().getShowPageBtn() / 2;
        this.endPage = nowPage + this.getSkinnable().getShowPageBtn() / 2;

        if (getEndPage() > maxPage) {
            this.startPage -= this.getEndPage() - maxPage;
            this.endPage = maxPage;
        }

        if (this.getStartPage() <= 0) {

            this.endPage = this.getEndPage() - this.getStartPage() + 1;
            this.startPage = 1;
        }

        if (this.getStartPage() <= 0 || this.getEndPage() > maxPage) {
            return;
        }
    }

    /**
     * 修改toggleButton的页码
     */
    private void changePageButton() {
        if (this.getSkinnable().getMaxPage() == 1) {
            this.centerBox.getChildren().clear();
            return;
        }
        int buttonIndex = 0;
        for (int i = this.getStartPage(); i <= this.getEndPage() && buttonIndex < this.showToggleButtonList.size(); i++) {
            ToggleButton tb = this.showToggleButtonList.get(buttonIndex);
            buttonIndex++;
            tb.setText(i + "");
            LOGGER.debug("changePageButton=>toggleButton:" + i + " ;this.getSkinnable().getNowPage()");
            if (i == this.getSkinnable().getNowPage()) {
                tb.setSelected(true);
            }
        }

        this.modifyMoreButton();

        //取消非页码按钮的选中状态
        ToggleButton select = (ToggleButton) this.toggleButtons.getSelectedToggle();
        try {
            Integer.parseInt(select.getText());
        } catch (Exception e) {
            if (select != null) {
                select.setSelected(false);
            }
        }
    }

    /**
     * 计算添加或删除morebutton
     */
    private void modifyMoreButton() {
        if (this.moreBtn == null) {
            return;
        }

        if (this.getSkinnable().getMaxPage() < this.getSkinnable().getNowPage()) {
            this.removeNode(this.moreBtn);
            return;
        }
        if (this.endPage == this.getSkinnable().getMaxPage()) {
            this.removeNode(this.moreBtn);

            return;
        }
        if (!this.centerBox.getChildren().contains(this.moreBtn)) {
            this.addMoreButton();
            return;
        }
    }

    /**
     * 添加more button
     */
    private void addMoreButton() {

        for (int i = this.centerBox.getChildren().size() - 1; i > 0; i--) {
            if (this.centerBox.getChildren().get(i) instanceof ToggleButton) {
                ToggleButton t = (ToggleButton) this.centerBox.getChildren().get(i);
                if (t.getId().equals(NEXT_PAGE_BUTTON_ID)) {
                    LOGGER.debug("addMoreButton=>addMoreButton:" + (i));
                    this.centerBox.getChildren().add(i, this.moreBtn);
                    this.moreBtn.setSelected(false);
                    break;
                }
            }

        }
    }

    /**
     * @return the startPage
     */
    public int getStartPage() {
        return startPage;
    }

    /**
     * @return the endPage
     */
    public int getEndPage() {
        return endPage;
    }

    /**
     * 处理用户点击事件
     */
    private class ButtonClickEventHandler implements EventHandler {

        @Override
        public void handle(Event t) {
            int nowPage = getSkinnable().getNowPage();

            ToggleButton button = (ToggleButton) t.getSource();
            switch (button.getId()) {
                case FIRST_PAGE_BUTTON_ID:
                    nowPage = 1;
                    break;
                case LAST_PAGE_BUTTON_ID:
                    nowPage = getSkinnable().getMaxPage();
                    break;
                case PRE_PAGE_BUTTON_ID:
                    if (nowPage > 1) {
                        nowPage -= 1;
                    }
                    break;
                case NEXT_PAGE_BUTTON_ID:
                    if (nowPage < getSkinnable().getMaxPage()) {
                        nowPage += 1;
                    }
                    break;
                case MORE_PAGE_BUTTON_ID:
                    if (getSkinnable().getShowPageBtn() == 0) {
                        return;
                    }

                    nowPage = getEndPage() + getSkinnable().getShowPageBtn() / 2 + 1;
                    if (nowPage > getSkinnable().getMaxPage()) {
                        nowPage = getSkinnable().getMaxPage();
                    }
                    if (toggleButtons.getSelectedToggle() != null) {
                        toggleButtons.getSelectedToggle().setSelected(false);
                    }

                    countStartAndEndPage(nowPage);
                    changePageButton();
                    button.setSelected(false);
                    return;
                case INPUT_PAGENUM_ID:
                    inputPage.setText("");
                    break;
                default:
                    String pageNumber = button.getText();
                    int page = Integer.parseInt(pageNumber);
                    if (page == nowPage) {
                        button.setSelected(true);
                        return;
                    }
                    nowPage = page;

            }

            getSkinnable().setNowPage(nowPage);
        }

    }

    /**
     * 带回车的输入框
     */
    private class EnterTextFiled extends DecorationPane {

        private TextField textField;
        private String defaultText;
        private ImageView imageView;

        public EnterTextFiled(String text) {
            super(new TextField(text));
            this.defaultText = text;
            this.initHandler();
        }

        /**
         * 初始化事件
         */
        private void initHandler() {
            textField.setOnMouseClicked(new EventHandler() {
                @Override
                public void handle(Event t) {
                    if (textField.getText().equals(defaultText)) {
                        textField.setText("");
                    }
                }
            });

            this.setOnKeyPressed(new EventHandler<KeyEvent>() {

                @Override
                public void handle(KeyEvent keyEvent) {
                    KeyCode keyCode = keyEvent.getCode();
                    if (keyCode == KeyCode.ENTER) {
                        String pageNumber = getText();
                        changeNowPage(pageNumber);
                    } else if (getText().equals(defaultText)) {
                        setText("");
                    }
                }
            });
        }

        @Override
        protected void initializeChildren() {
            textField = (TextField) getContent();
            imageView = new ImageView();
            imageView.setId(INPUT_IMAGE_ID);
            imageView.getStyleClass().add("image-view-pagebar");
            this.textField.getStyleClass().add("text-field-unclick");
            this.imageView.setOnMouseClicked(new EventHandler() {

                @Override
                public void handle(Event t) {

                    changeNowPage(textField.getText());

                }
            });
            DecorationUtils.install(textField, new Decorator<>(imageView, Pos.CENTER_RIGHT, new Point2D(-76, 0)));
        }

        @Override
        protected void initializeStyle() {
            super.initializeStyle();
            // this.
        }

        /**
         * 设置文本框文本
         *
         * @param text
         */
        public void setText(String text) {
            textField = (TextField) getContent();
            textField.setText(text);
        }

        /**
         * 获取文本框文本
         *
         * @return
         */
        public String getText() {
            textField = (TextField) getContent();
            return textField.getText().trim();
        }

    }

}
