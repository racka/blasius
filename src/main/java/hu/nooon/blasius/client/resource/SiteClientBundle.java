package hu.nooon.blasius.client.resource;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.DataResource;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.resources.client.TextResource;

public interface SiteClientBundle extends ClientBundle {

    public static final SiteClientBundle INSTANCE =  GWT.create(SiteClientBundle.class);

//    @ImageResource.ImageOptions(repeatStyle = ImageResource.RepeatStyle.None)
//    @Source("hu/nooon/blasius/public/images/background/background.jpg")
//    public ImageResource background();
//
//
//
//    @Source("hu/nooon/blasius/public/images/archive/cover/archive_cover.jpg")
//    public ImageResource archive_cover();
//    @Source("hu/nooon/blasius/public/images/exhibitions/cover/exhibitions_cover.jpg")
//    public ImageResource exhibitions_cover();
//    @Source("hu/nooon/blasius/public/images/endorsers/cover/endorsers_cover.jpg")
//    public ImageResource endorsers_cover();
//    @Source("hu/nooon/blasius/public/images/new_series/cover/new_cover.jpg")
//    public ImageResource new_cover();
//    @Source("hu/nooon/blasius/public/images/owners/cover/owners_cover.jpg")
//    public ImageResource owners_cover();
//
//
//    @Source("hu/nooon/blasius/public/images/new_series/185180_391506010915591_1921493683_n.jpg")
//    public ImageResource n1();
//    @Source("hu/nooon/blasius/public/images/new_series/199033_391506070915585_1643436659_n.jpg")
//    public ImageResource n2();
//    @Source("hu/nooon/blasius/public/images/new_series/199638_391505420915650_1412439018_n.jpg")
//    public ImageResource n3();
//    @Source("hu/nooon/blasius/public/images/new_series/206199_391504944249031_40153279_n-1.jpg")
//    public ImageResource n4();
//    @Source("hu/nooon/blasius/public/images/new_series/206199_391504944249031_40153279_n.jpg")
//    public ImageResource n5();
//    @Source("hu/nooon/blasius/public/images/new_series/207045_391505037582355_490251293_n.jpg")
//    public ImageResource n6();
//    @Source("hu/nooon/blasius/public/images/new_series/216821_391505437582315_1235918165_n.jpg")
//    public ImageResource n7();
//    @Source("hu/nooon/blasius/public/images/new_series/223959_391742607558598_489983486_n.jpg")
//    public ImageResource n8();
//    @Source("hu/nooon/blasius/public/images/new_series/225083_391742594225266_1747956719_n.jpg")
//    public ImageResource n9();
//    @Source("hu/nooon/blasius/public/images/new_series/225113_391505064249019_660786603_n.jpg")
//    public ImageResource n10();
//
//
//    @Source("hu/nooon/blasius/public/images/archive/196819_391547204244805_1722238409_n.jpg")
//    public ImageResource ar1();
//    @Source("hu/nooon/blasius/public/images/archive/199063_391467970919395_1220269768_n.jpg")
//    public ImageResource ar2();
//    @Source("hu/nooon/blasius/public/images/archive/199633_391466307586228_1708015613_n.jpg")
//    public ImageResource ar3();
//    @Source("hu/nooon/blasius/public/images/archive/253886_391466677586191_349550515_n.jpg")
//    public ImageResource ar4();
//    @Source("hu/nooon/blasius/public/images/archive/206072_391466134252912_537412308_n.jpg")
//    public ImageResource ar5();
//    @Source("hu/nooon/blasius/public/images/archive/284695_391547130911479_963407813_n.jpg")
//    public ImageResource ar6();
//    @Source("hu/nooon/blasius/public/images/archive/292007_391547090911483_1586865831_n.jpg")
//    public ImageResource ar7();
//    @Source("hu/nooon/blasius/public/images/archive/292983_391467777586081_1459695051_n.jpg")
//    public ImageResource ar8();
//    @Source("hu/nooon/blasius/public/images/archive/295277_391466904252835_1361219165_n.jpg")
//    public ImageResource ar9();
//    @Source("hu/nooon/blasius/public/images/archive/320117_391467814252744_60283174_n.jpg")
//    public ImageResource ar10();
//    @Source("hu/nooon/blasius/public/images/archive/3259_391547220911470_1127686820_n.jpg")
//    public ImageResource ar11();
//
//
//    @Source("hu/nooon/blasius/public/images/exhibitions/185168_391537500912442_585749475_n.jpg")
//    public ImageResource ex1();
//    @Source("hu/nooon/blasius/public/images/exhibitions/199141_391537680912424_2083078761_n.jpg")
//    public ImageResource ex2();
//    @Source("hu/nooon/blasius/public/images/exhibitions/199141_391540164245509_1915857430_n.jpg")
//    public ImageResource ex3();
//    @Source("hu/nooon/blasius/public/images/exhibitions/199719_391537724245753_22808142_n.jpg")
//    public ImageResource ex4();
//    @Source("hu/nooon/blasius/public/images/exhibitions/207025_391540290912163_281462049_n.jpg")
//    public ImageResource ex5();
//    @Source("hu/nooon/blasius/public/images/exhibitions/207055_391541457578713_1821910266_n.jpg")
//    public ImageResource ex6();
//    @Source("hu/nooon/blasius/public/images/exhibitions/207068_391536957579163_214947298_n.jpg")
//    public ImageResource ex7();
//    @Source("hu/nooon/blasius/public/images/exhibitions/216172_391537640912428_569647312_n.jpg")
//    public ImageResource ex8();
//    @Source("hu/nooon/blasius/public/images/exhibitions/216896_391537184245807_189510525_n.jpg")
//    public ImageResource ex9();
//    @Source("hu/nooon/blasius/public/images/exhibitions/217912_391536750912517_605761152_n.jpg")
//    public ImageResource ex10();
//
//    @Source("hu/nooon/blasius/public/images/owners/184116_395070960559096_381294247_n.jpg")
//    public ImageResource o1();
//    @Source("hu/nooon/blasius/public/images/owners/185149_395070980559094_731334484_n.jpg")
//    public ImageResource o2();
//    @Source("hu/nooon/blasius/public/images/owners/291926_391740284225497_430685365_n.jpg")
//    public ImageResource o3();
//    @Source("hu/nooon/blasius/public/images/owners/304631_391956637537195_1120324850_n.jpg")
//    public ImageResource o4();
//    @Source("hu/nooon/blasius/public/images/owners/420896_402712936461565_211204547_n.jpg")
//    public ImageResource o5();
//    @Source("hu/nooon/blasius/public/images/owners/488234_391954004204125_1217028026_n.jpg")
//    public ImageResource o6();
//    @Source("hu/nooon/blasius/public/images/owners/523202_391777567555102_1624064644_n.jpg")
//    public ImageResource o7();
//    @Source("hu/nooon/blasius/public/images/owners/524522_391740300892162_1036240401_n.jpg")
//    public ImageResource o8();
//    @Source("hu/nooon/blasius/public/images/owners/542268_391778477555011_1391848941_n.jpg")
//    public ImageResource o9();
//    @Source("hu/nooon/blasius/public/images/owners/550373_402414583158067_372299087_n.jpg")
//    public ImageResource o10();
//
//    @Source("hu/nooon/blasius/public/images/endorsers/e1.jpg")
//    public ImageResource e1();
//
//
//
//    @Source("hu/nooon/blasius/public/texts/about.txt")
//    public TextResource about();

}
