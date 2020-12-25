package com.example.anna;

public class OnBoardingInfor {
    public Integer slideHeading;
    public Integer slideDesc;
    public Integer slideImage;

    public OnBoardingInfor(Integer image, Integer heading,Integer desc){
        slideImage = image;
        slideHeading = heading;
        slideDesc = desc;
    }


    public Integer getSlideDesc() {
        return slideDesc;
    }

    public Integer getSlideHeading() {
        return slideHeading;
    }

    public Integer getSlideImage() {
        return slideImage;
    }

    public void setSlideDesc(Integer slideDesc) {
        this.slideDesc = slideDesc;
    }

    public void setSlideHeading(Integer slideHeading) {
        this.slideHeading = slideHeading;
    }

    public void setSlideImage(Integer slideImage) {
        this.slideImage = slideImage;
    }
}
