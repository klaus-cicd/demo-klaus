package com.silas.demo.easyexcel.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 舱水测量记录
 *
 * @author Klaus
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("sg_bilge_measurement_record")
public class BilgeMeasurementRecord extends VesselEntity {

    /**
     * 日志ID
     */
    private Long logbookId;

    /**
     * 左饮水柜
     */
    private String leftWaterDispenser;

    /**
     * 中饮水柜
     */
    private String middleWaterDispenser;

    /**
     * 右饮水柜
     */
    private String rightWaterDispenser;

    /**
     * 左压载水舱1
     */
    @TableField("left_ballast_tank_1")
    private String leftBallastTank1;

    /**
     * 左压载水舱2
     */
    @TableField("left_ballast_tank_2")
    private String leftBallastTank2;

    /**
     * 左压载水舱3
     */
    @TableField("left_ballast_tank_3")
    private String leftBallastTank3;

    /**
     * 左压载水舱4
     */
    @TableField("left_ballast_tank_4")
    private String leftBallastTank4;

    /**
     * 左压载水舱5
     */
    @TableField("left_ballast_tank_5")
    private String leftBallastTank5;

    /**
     * 左压载水舱6
     */
    @TableField("left_ballast_tank_6")
    private String leftBallastTank6;

    /**
     * 左压载水舱7
     */
    @TableField("left_ballast_tank_7")
    private String leftBallastTank7;

    /**
     * 左压载水舱8
     */
    @TableField("left_ballast_tank_8")
    private String leftBallastTank8;

    /**
     * 左压载水舱9
     */
    @TableField("left_ballast_tank_9")
    private String leftBallastTank9;

    /**
     * 左压载水舱10
     */
    @TableField("left_ballast_tank_10")
    private String leftBallastTank10;

    /**
     * 左压载水舱11
     */
    @TableField("left_ballast_tank_11")
    private String leftBallastTank11;

    /**
     * 左压载水舱12
     */
    @TableField("left_ballast_tank_12")
    private String leftBallastTank12;

    /**
     * 左压载水舱13
     */
    @TableField("left_ballast_tank_13")
    private String leftBallastTank13;

    /**
     * 右压载水舱1
     */
    @TableField("right_ballast_tank_1")
    private String rightBallastTank1;

    /**
     * 右压载水舱2
     */
    @TableField("right_ballast_tank_2")
    private String rightBallastTank2;

    /**
     * 右压载水舱3
     */
    @TableField("right_ballast_tank_3")
    private String rightBallastTank3;

    /**
     * 右压载水舱4
     */
    @TableField("right_ballast_tank_4")
    private String rightBallastTank4;

    /**
     * 右压载水舱5
     */
    @TableField("right_ballast_tank_5")
    private String rightBallastTank5;

    /**
     * 右压载水舱6
     */
    @TableField("right_ballast_tank_6")
    private String rightBallastTank6;

    /**
     * 右压载水舱7
     */
    @TableField("right_ballast_tank_7")
    private String rightBallastTank7;

    /**
     * 右压载水舱8
     */
    @TableField("right_ballast_tank_8")
    private String rightBallastTank8;

    /**
     * 右压载水舱9
     */
    @TableField("right_ballast_tank_9")
    private String rightBallastTank9;

    /**
     * 右压载水舱10
     */
    @TableField("right_ballast_tank_10")
    private String rightBallastTank10;

    /**
     * 右压载水舱11
     */
    @TableField("right_ballast_tank_11")
    private String rightBallastTank11;

    /**
     * 右压载水舱12
     */
    @TableField("right_ballast_tank_12")
    private String rightBallastTank12;

    /**
     * 右压载水舱13
     */
    @TableField("right_ballast_tank_13")
    private String rightBallastTank13;

    /**
     * 中压载水舱1
     */
    @TableField("middle_ballast_tank_1")
    private String middleBallastTank1;


    /**
     * 中压载水舱2
     */
    @TableField("middle_ballast_tank_2")
    private String middleBallastTank2;


    /**
     * 中压载水舱3
     */
    @TableField("middle_ballast_tank_3")
    private String middleBallastTank3;


    /**
     * 中压载水舱4
     */
    @TableField("middle_ballast_tank_4")
    private String middleBallastTank4;


    /**
     * 中压载水舱5
     */
    @TableField("middle_ballast_tank_5")
    private String middleBallastTank5;


    /**
     * 中压载水舱6
     */
    @TableField("middle_ballast_tank_6")
    private String middleBallastTank6;


    /**
     * 中压载水舱7
     */
    @TableField("middle_ballast_tank_7")
    private String middleBallastTank7;


    /**
     * 中压载水舱8
     */
    @TableField("middle_ballast_tank_8")
    private String middleBallastTank8;


    /**
     * 中压载水舱9
     */
    @TableField("middle_ballast_tank_9")
    private String middleBallastTank9;

    /**
     * 中压载水舱10
     */
    @TableField("middle_ballast_tank_10")
    private String middleBallastTank10;

    /**
     * 中压载水舱11
     */
    @TableField("middle_ballast_tank_11")
    private String middleBallastTank11;


    /**
     * 中压载水舱12
     */
    @TableField("middle_ballast_tank_12")
    private String middleBallastTank12;


    /**
     * 中压载水舱13
     */
    @TableField("middle_ballast_tank_13")
    private String middleBallastTank13;

    /**
     * 污水沟左1
     */
    private String leftSewer1;

    /**
     * 污水沟左2
     */
    private String leftSewer2;

    /**
     * 污水沟左3
     */
    private String leftSewer3;

    /**
     * 污水沟左4
     */
    private String leftSewer4;

    /**
     * 污水沟左5
     */
    private String leftSewer5;

    /**
     * 污水沟左6
     */
    private String leftSewer6;

    /**
     * 污水沟左7
     */
    private String leftSewer7;


    /**
     * 污水沟中1
     */
    private String middleSewer1;

    /**
     * 污水沟中2
     */
    private String middleSewer2;

    /**
     * 污水沟中3
     */
    private String middleSewer3;

    /**
     * 污水沟中4
     */
    private String middleSewer4;

    /**
     * 污水沟中5
     */
    private String middleSewer5;

    /**
     * 污水沟中6
     */
    private String middleSewer6;

    /**
     * 污水沟中7
     */
    private String middleSewer7;

    /**
     * 污水沟右1
     */
    @TableField("right_sewer1")
    private String rightSewer1;

    /**
     * 污水沟右2
     */
    @TableField("right_sewer2")
    private String rightSewer2;

    /**
     * 污水沟右3
     */
    @TableField("right_sewer3")
    private String rightSewer3;

    /**
     * 污水沟右4
     */
    @TableField("right_sewer4")
    private String rightSewer4;

    /**
     * 污水沟右5
     */
    @TableField("right_sewer5")
    private String rightSewer5;

    /**
     * 污水沟右6
     */
    @TableField("right_sewer6")
    private String rightSewer6;

    /**
     * 污水沟右7
     */
    @TableField("right_sewer7")
    private String rightSewer7;
}
