package com.phjt.shangxueyuan.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.phjt.shangxueyuan.mvp.ui.adapter.ExerciseShowAdapter;

import java.util.List;

public class ExerciseShowBean implements MultiItemEntity {
    /**
     * exerciseUserRecordId : 33
     * exerciseId : 17
     * userId : 6003
     * photo : null
     * nickName : 159****9086
     * likeNum : 0
     * likeState : 0
     * exerciseRecordState : 1
     * addTime : 2021-03-23 14:37:25
     * recordVos : [{"questionType":3,"answerState":0,"userAnswer":"992","rightShoice":"990","optionVos":[{"optionId":990,"optionName":"A","optionContent":"正确"},{"optionId":992,"optionName":"B","optionContent":"错误"}]}]
     */

    private String exerciseUserRecordId;
    private int exerciseId;
    private int userId;
    private String photo;
    private String nickName;
    private int likeNum;
    private String likeState;
    private int exerciseRecordState;
    private String addTime;
    private List<RecordVosBean> recordVos;
    private List<ExerciseItemBean> questionVoList;
    private int anwserType = 0;
    private int state;
    public int getAnwserType() {
        return anwserType;
    }

    public void setAnwserType(int anwserType) {
        this.anwserType = anwserType;
    }

    @Override
    public int getItemType() {
        if (questionVoList != null) {
            return ExerciseShowAdapter.TYPE_LEVEL_0;
        }
        if (recordVos != null) {
            return ExerciseShowAdapter.TYPE_LEVEL_1;
        }
        return 1;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getExerciseUserRecordId() {
        return exerciseUserRecordId;
    }

    public void setExerciseUserRecordId(String exerciseUserRecordId) {
        this.exerciseUserRecordId = exerciseUserRecordId;
    }

    public int getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(int exerciseId) {
        this.exerciseId = exerciseId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getLikeNum() {
        return likeNum;
    }

    public void setLikeNum(int likeNum) {
        this.likeNum = likeNum;
    }

    public String getLikeState() {
        return likeState;
    }

    public void setLikeState(String likeState) {
        this.likeState = likeState;
    }

    public int getExerciseRecordState() {
        return exerciseRecordState;
    }

    public void setExerciseRecordState(int exerciseRecordState) {
        this.exerciseRecordState = exerciseRecordState;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public List<RecordVosBean> getRecordVos() {
        return recordVos;
    }

    public void setRecordVos(List<RecordVosBean> recordVos) {
        this.recordVos = recordVos;
    }

    public static class RecordVosBean {
        /**
         * questionType : 3
         * answerState : 0
         * userAnswer : 992
         * rightShoice : 990
         * optionVos : [{"optionId":990,"optionName":"A","optionContent":"正确"},{"optionId":992,"optionName":"B","optionContent":"错误"}]
         */

        private int questionType;
        private int answerState;
        private String userAnswer;
        private String rightShoice;
        private String userAnswerImg;
        private List<OptionVosBean> optionVos;

        public String getUserAnswerImg() {
            return userAnswerImg;
        }

        public void setUserAnswerImg(String userAnswerImg) {
            this.userAnswerImg = userAnswerImg;
        }

        public int getQuestionType() {
            return questionType;
        }

        public void setQuestionType(int questionType) {
            this.questionType = questionType;
        }

        public int getAnswerState() {
            return answerState;
        }

        public void setAnswerState(int answerState) {
            this.answerState = answerState;
        }

        public String getUserAnswer() {
            if (null==userAnswer){
                return "";
            }
            return userAnswer;
        }

        public void setUserAnswer(String userAnswer) {
            this.userAnswer = userAnswer;
        }

        public String getRightShoice() {
            return rightShoice;
        }

        public void setRightShoice(String rightShoice) {
            this.rightShoice = rightShoice;
        }

        public List<OptionVosBean> getOptionVos() {
            return optionVos;
        }

        public void setOptionVos(List<OptionVosBean> optionVos) {
            this.optionVos = optionVos;
        }

        public static class OptionVosBean {
            /**
             * optionId : 990
             * optionName : A
             * optionContent : 正确
             */

            private String optionId;
            private String optionName;
            private String optionContent;

            public String getOptionId() {
                return optionId;
            }

            public void setOptionId(String optionId) {
                this.optionId = optionId;
            }

            public String getOptionName() {
                return optionName;
            }

            public void setOptionName(String optionName) {
                this.optionName = optionName;
            }

            public String getOptionContent() {
                return optionContent;
            }

            public void setOptionContent(String optionContent) {
                this.optionContent = optionContent;
            }
        }
    }

    public List<ExerciseItemBean> getQuestionVoList() {
        return questionVoList;
    }

    public void setQuestionVoList(List<ExerciseItemBean> questionVoList) {
        this.questionVoList = questionVoList;
    }

}
