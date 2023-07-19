package com.cbs.middleware.config;

import com.cbs.middleware.domain.AccountHolderMaster;
import com.cbs.middleware.domain.ActivityType;
import com.cbs.middleware.domain.CastCategoryMaster;
import com.cbs.middleware.domain.CropMaster;
import com.cbs.middleware.domain.FarmerCategoryMaster;
import com.cbs.middleware.domain.FarmerTypeMaster;
import com.cbs.middleware.domain.LandTypeMaster;
import com.cbs.middleware.domain.OccupationMaster;
import com.cbs.middleware.domain.RelativeMaster;
import com.cbs.middleware.domain.SeasonMaster;
import com.cbs.middleware.repository.AccountHolderMasterRepository;
import com.cbs.middleware.repository.ActivityTypeRepository;
import com.cbs.middleware.repository.CastCategoryMasterRepository;
import com.cbs.middleware.repository.CropMasterRepository;
import com.cbs.middleware.repository.FarmerCategoryMasterRepository;
import com.cbs.middleware.repository.FarmerTypeMasterRepository;
import com.cbs.middleware.repository.LandTypeMasterRepository;
import com.cbs.middleware.repository.OccupationMasterRepository;
import com.cbs.middleware.repository.RelativeMasterRepository;
import com.cbs.middleware.repository.SeasonMasterRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class MasterDataCacheService implements ApplicationRunner {

    @Autowired
    private CastCategoryMasterRepository castCategoryMasterRepository;

    @Autowired
    private FarmerTypeMasterRepository farmerTypeMasterRepository;

    @Autowired
    private FarmerCategoryMasterRepository farmerCategoryMasterRepository;

    @Autowired
    private OccupationMasterRepository occupationMasterRepository;

    @Autowired
    private RelativeMasterRepository relativeMasterRepository;

    @Autowired
    private AccountHolderMasterRepository accountHolderMasterRepository;

    @Autowired
    private LandTypeMasterRepository landTypeMasterRepository;

    @Autowired
    private SeasonMasterRepository seasonMasterRepository;

    @Autowired
    private ActivityTypeRepository activityTypeRepository;

    @Autowired
    private CropMasterRepository cropMasterRepository;

    public static List<CastCategoryMaster> CastCategoryMasterList = new ArrayList<>();
    public static List<FarmerTypeMaster> FarmerTypeMasterList = new ArrayList<>();
    public static List<FarmerCategoryMaster> FarmerCategoryMasterList = new ArrayList<>();
    public static List<OccupationMaster> OccupationMasterList = new ArrayList<>();
    public static List<RelativeMaster> RelativeMasterList = new ArrayList<>();
    public static List<AccountHolderMaster> AccountHolderMasterList = new ArrayList<>();
    public static List<LandTypeMaster> LandTypeMasterList = new ArrayList<>();
    public static List<SeasonMaster> SeasonMasterList = new ArrayList<>();
    public static List<ActivityType> ActivityTypeMasterList = new ArrayList<>();
    public static List<CropMaster> CropMasterList = new ArrayList<>();

    @Override
    public void run(ApplicationArguments args) throws Exception {
        try {
            CastCategoryMasterList.addAll(castCategoryMasterRepository.findAll());
            FarmerTypeMasterList.addAll(farmerTypeMasterRepository.findAll());
            FarmerCategoryMasterList.addAll(farmerCategoryMasterRepository.findAll());
            OccupationMasterList.addAll(occupationMasterRepository.findAll());
            RelativeMasterList.addAll(relativeMasterRepository.findAll());
            AccountHolderMasterList.addAll(accountHolderMasterRepository.findAll());
            LandTypeMasterList.addAll(landTypeMasterRepository.findAll());
            SeasonMasterList.addAll(seasonMasterRepository.findAll());
            ActivityTypeMasterList.addAll(activityTypeRepository.findAll());
            CropMasterList.addAll(cropMasterRepository.findAll());
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error in MasterDataCacheService.CastCategoryMasterList" + e);
        }

        System.out.println("+++++++++++++++++++++++++" + CastCategoryMasterList);
        System.out.println("+++++++++++++++++++++++++" + FarmerTypeMasterList);
        System.out.println("+++++++++++++++++++++++++" + FarmerCategoryMasterList);
        System.out.println("+++++++++++++++++++++++++" + OccupationMasterList);
        System.out.println("+++++++++++++++++++++++++" + RelativeMasterList);
        System.out.println("+++++++++++++++++++++++++" + AccountHolderMasterList);
        System.out.println("+++++++++++++++++++++++++" + LandTypeMasterList);
        System.out.println("+++++++++++++++++++++++++" + SeasonMasterList);
        System.out.println("+++++++++++++++++++++++++" + ActivityTypeMasterList);
        System.out.println("+++++++++++++++++++++++++" + CropMasterList);
    }
}
