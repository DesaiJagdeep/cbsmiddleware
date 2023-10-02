import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'iss-file-parser',
        data: { pageTitle: 'cbsMiddlewareApp.issFileParser.home.title' },
        loadChildren: () => import('./iss-file-parser/iss-file-parser.module').then(m => m.IssFileParserModule),
      },
      {
        path: 'state-master',
        data: { pageTitle: 'cbsMiddlewareApp.stateMaster.home.title' },
        loadChildren: () => import('./state-master/state-master.module').then(m => m.StateMasterModule),
      },
      {
        path: 'district-master',
        data: { pageTitle: 'cbsMiddlewareApp.districtMaster.home.title' },
        loadChildren: () => import('./district-master/district-master.module').then(m => m.DistrictMasterModule),
      },
      {
        path: 'taluka-master',
        data: { pageTitle: 'cbsMiddlewareApp.talukaMaster.home.title' },
        loadChildren: () => import('./taluka-master/taluka-master.module').then(m => m.TalukaMasterModule),
      },
      {
        path: 'bank-master',
        data: { pageTitle: 'cbsMiddlewareApp.bankMaster.home.title' },
        loadChildren: () => import('./bank-master/bank-master.module').then(m => m.BankMasterModule),
      },
      {
        path: 'bank-branch-master',
        data: { pageTitle: 'cbsMiddlewareApp.bankBranchMaster.home.title' },
        loadChildren: () => import('./bank-branch-master/bank-branch-master.module').then(m => m.BankBranchMasterModule),
      },
      {
        path: 'pacs-master',
        data: { pageTitle: 'cbsMiddlewareApp.pacsMaster.home.title' },
        loadChildren: () => import('./pacs-master/pacs-master.module').then(m => m.PacsMasterModule),
      },
      {
        path: 'cast-category-master',
        data: { pageTitle: 'cbsMiddlewareApp.castCategoryMaster.home.title' },
        loadChildren: () => import('./cast-category-master/cast-category-master.module').then(m => m.CastCategoryMasterModule),
      },
      {
        path: 'category-master',
        data: { pageTitle: 'cbsMiddlewareApp.categoryMaster.home.title' },
        loadChildren: () => import('./category-master/category-master.module').then(m => m.CategoryMasterModule),
      },
      {
        path: 'relative-master',
        data: { pageTitle: 'cbsMiddlewareApp.relativeMaster.home.title' },
        loadChildren: () => import('./relative-master/relative-master.module').then(m => m.RelativeMasterModule),
      },
      {
        path: 'crop-master',
        data: { pageTitle: 'cbsMiddlewareApp.cropMaster.home.title' },
        loadChildren: () => import('./crop-master/crop-master.module').then(m => m.CropMasterModule),
      },
      {
        path: 'crop-category-master',
        data: { pageTitle: 'cbsMiddlewareApp.cropCategoryMaster.home.title' },
        loadChildren: () => import('./crop-category-master/crop-category-master.module').then(m => m.CropCategoryMasterModule),
      },
      {
        path: 'occupation-master',
        data: { pageTitle: 'cbsMiddlewareApp.occupationMaster.home.title' },
        loadChildren: () => import('./occupation-master/occupation-master.module').then(m => m.OccupationMasterModule),
      },
      {
        path: 'designation-master',
        data: { pageTitle: 'cbsMiddlewareApp.designationMaster.home.title' },
        loadChildren: () => import('./designation-master/designation-master.module').then(m => m.DesignationMasterModule),
      },
      {
        path: 'account-holder-master',
        data: { pageTitle: 'cbsMiddlewareApp.accountHolderMaster.home.title' },
        loadChildren: () => import('./account-holder-master/account-holder-master.module').then(m => m.AccountHolderMasterModule),
      },
      {
        path: 'farmer-category-master',
        data: { pageTitle: 'cbsMiddlewareApp.farmerCategoryMaster.home.title' },
        loadChildren: () => import('./farmer-category-master/farmer-category-master.module').then(m => m.FarmerCategoryMasterModule),
      },
      {
        path: 'farmer-type-master',
        data: { pageTitle: 'cbsMiddlewareApp.farmerTypeMaster.home.title' },
        loadChildren: () => import('./farmer-type-master/farmer-type-master.module').then(m => m.FarmerTypeMasterModule),
      },
      {
        path: 'land-type-master',
        data: { pageTitle: 'cbsMiddlewareApp.landTypeMaster.home.title' },
        loadChildren: () => import('./land-type-master/land-type-master.module').then(m => m.LandTypeMasterModule),
      },
      {
        path: 'season-master',
        data: { pageTitle: 'cbsMiddlewareApp.seasonMaster.home.title' },
        loadChildren: () => import('./season-master/season-master.module').then(m => m.SeasonMasterModule),
      },
      {
        path: 'iss-portal-file',
        data: { pageTitle: 'cbsMiddlewareApp.issPortalFile.home.title' },
        loadChildren: () => import('./iss-portal-file/iss-portal-file.module').then(m => m.IssPortalFileModule),
      },
      {
        path: 'batch-transaction',
        data: { pageTitle: 'cbsMiddlewareApp.batchTransaction.home.title' },
        loadChildren: () => import('./batch-transaction/batch-transaction.module').then(m => m.BatchTransactionModule),
      },
      {
        path: 'application-log',
        data: { pageTitle: 'cbsMiddlewareApp.applicationLog.home.title' },
        loadChildren: () => import('./application-log/application-log.module').then(m => m.ApplicationLogModule),
      },
      {
        path: 'application-log-history',
        data: { pageTitle: 'cbsMiddlewareApp.applicationLogHistory.home.title' },
        loadChildren: () => import('./application-log-history/application-log-history.module').then(m => m.ApplicationLogHistoryModule),
      },
      {
        path: 'application',
        data: { pageTitle: 'cbsMiddlewareApp.application.home.title' },
        loadChildren: () => import('./application/application.module').then(m => m.ApplicationModule),
      },
      {
        path: 'court-case-setting',
        data: { pageTitle: 'cbsMiddlewareApp.courtCaseSetting.home.title' },
        loadChildren: () => import('./court-case-setting/court-case-setting.module').then(m => m.CourtCaseSettingModule),
      },
      {
        path: 'activity-type',
        data: { pageTitle: 'cbsMiddlewareApp.activityType.home.title' },
        loadChildren: () => import('./activity-type/activity-type.module').then(m => m.ActivityTypeModule),
      },
      {
        path: 'notification',
        data: { pageTitle: 'cbsMiddlewareApp.notification.home.title' },
        loadChildren: () => import('./notification/notification.module').then(m => m.NotificationModule),
      },
      {
        path: 'cbs-data-report',
        data: { pageTitle: 'cbsMiddlewareApp.cbsDataReport.home.title' },
        loadChildren: () => import('./cbs-data-report/cbs-data-report.module').then(m => m.CbsDataReportModule),
      },
      {
        path: 'court-case',
        data: { pageTitle: 'cbsMiddlewareApp.courtCase.home.title' },
        loadChildren: () => import('./court-case/court-case.module').then(m => m.CourtCaseModule),
      },
      {
        path: 'court-case-details',
        data: { pageTitle: 'cbsMiddlewareApp.courtCaseDetails.home.title' },
        loadChildren: () => import('./court-case-details/court-case-details.module').then(m => m.CourtCaseDetailsModule),
      },
      {
        path: 'kamal-maryada-patrak',
        data: { pageTitle: 'cbsMiddlewareApp.kamalMaryadaPatrak.home.title' },
        loadChildren: () => import('./kamal-maryada-patrak/kamal-maryada-patrak.module').then(m => m.KamalMaryadaPatrakModule),
      },
      {
        path: 'loan-demand-km-patrak',
        data: { pageTitle: 'cbsMiddlewareApp.loanDemandKMPatrak.home.title' },
        loadChildren: () => import('./loan-demand-km-patrak/loan-demand-km-patrak.module').then(m => m.LoanDemandKMPatrakModule),
      },
      {
        path: 'kmp-upload',
        data: { pageTitle: 'cbsMiddlewareApp.kMPUpload.home.title' },
        loadChildren: () => import('./kmp-upload/kmp-upload.module').then(m => m.KMPUploadModule),
      },
      {
        path: 'karkhana-vasuli',
        data: { pageTitle: 'cbsMiddlewareApp.karkhanaVasuli.home.title' },
        loadChildren: () => import('./karkhana-vasuli/karkhana-vasuli.module').then(m => m.KarkhanaVasuliModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
