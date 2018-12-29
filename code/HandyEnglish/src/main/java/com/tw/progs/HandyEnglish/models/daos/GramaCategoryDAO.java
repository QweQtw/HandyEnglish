package com.tw.progs.HandyEnglish.models.daos;

import com.tw.progs.HandyEnglish.db.myBatis.dtos.GramaCategory;
import com.tw.progs.HandyEnglish.db.myBatis.mappers.GramaCategoriesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GramaCategoryDAO {

    private GramaCategoriesMapper gcm;
    private ProfilesDAO pd;

    @Autowired
    public GramaCategoryDAO(GramaCategoriesMapper cm, ProfilesDAO pd){
        this.gcm = cm;
        this.pd = pd;
    }

    public Integer resolveID(String cat, Integer profileId) {

        cat = (cat==null||cat.trim().isEmpty())?"":cat;

        if (cat.isEmpty()){
            GramaCategory ctg = gcm.getAllCategories(pd.getDefUserAccountId()).get(0);
            ctg.setProf(profileId);
            ctg.setId(-1);
            GramaCategory tmp = gcm.findCategory(ctg.getCategory(), profileId);
            if (tmp==null)
                gcm.insertCategory(ctg);
            return tmp.getId();
        }else{
            GramaCategory category = gcm.findCategory(cat, profileId);
            if (category!=null&&category.getId()>0) {
                return category.getId();
            }else {
                category = new GramaCategory();
                category.setProf(profileId);
                category.setCategory(cat);
                gcm.insertCategory(category);
                return category.getId();
            }
        }
    }
}
