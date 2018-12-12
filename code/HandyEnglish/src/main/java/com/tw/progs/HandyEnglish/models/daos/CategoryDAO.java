package com.tw.progs.HandyEnglish.models.daos;

import com.tw.progs.HandyEnglish.db.myBatis.dtos.Category;
import com.tw.progs.HandyEnglish.db.myBatis.mappers.CategoriesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CategoryDAO {

    private CategoriesMapper cm;
    private ProfilesDAO pd;

    @Autowired
    public CategoryDAO(CategoriesMapper cm, ProfilesDAO pd){
        this.cm = cm;
        this.pd = pd;
    }

    public Integer resolveID(String cat, Integer profileId) {

        cat = (cat==null||cat.trim().isEmpty())?"":cat;

        if (cat.isEmpty()){
            Category ctg = cm.getAllCategories(pd.getDefUserAccountId()).get(0);
            ctg.setProf(profileId);
            ctg.setId(-1);
            Category tmp = cm.findCategory(ctg.getCategory(), profileId);
            if (tmp==null)
                cm.insertCategory(ctg);
            return tmp.getId();
        }else{
            Category category = cm.findCategory(cat, profileId);
            if (category!=null&&category.getId()>0) {
                return category.getId();
            }else {
                category.setProf(profileId);
                category.setCategory(cat);
                cm.insertCategory(category);
                return category.getId();
            }
        }
    }
}
