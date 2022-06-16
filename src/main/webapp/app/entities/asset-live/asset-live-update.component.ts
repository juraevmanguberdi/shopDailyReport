import { Component, Vue, Inject } from 'vue-property-decorator';

import AlertService from '@/shared/alert/alert.service';

import { IAssetLive, AssetLive } from '@/shared/model/asset-live.model';
import AssetLiveService from './asset-live.service';

const validations: any = {
  assetLive: {
    totalAssets: {},
    currentAssets: {},
    moneyTotal: {},
    cashShop: {},
    cashOwner: {},
    bankAccount: {},
    goods: {},
    debitor: {},
    longTermAssets: {},
    transport: {},
    equipment: {},
    realEstate: {},
    other: {},
    code: {},
    notes: {},
  },
};

@Component({
  validations,
})
export default class AssetLiveUpdate extends Vue {
  @Inject('assetLiveService') private assetLiveService: () => AssetLiveService;
  @Inject('alertService') private alertService: () => AlertService;

  public assetLive: IAssetLive = new AssetLive();
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.assetLiveId) {
        vm.retrieveAssetLive(to.params.assetLiveId);
      }
    });
  }

  created(): void {
    this.currentLanguage = this.$store.getters.currentLanguage;
    this.$store.watch(
      () => this.$store.getters.currentLanguage,
      () => {
        this.currentLanguage = this.$store.getters.currentLanguage;
      }
    );
  }

  public save(): void {
    this.isSaving = true;
    if (this.assetLive.id) {
      this.assetLiveService()
        .update(this.assetLive)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('shopDailyReportApp.assetLive.updated', { param: param.id });
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        })
        .catch(error => {
          this.isSaving = false;
          this.alertService().showHttpError(this, error.response);
        });
    } else {
      this.assetLiveService()
        .create(this.assetLive)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('shopDailyReportApp.assetLive.created', { param: param.id });
          this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Success',
            variant: 'success',
            solid: true,
            autoHideDelay: 5000,
          });
        })
        .catch(error => {
          this.isSaving = false;
          this.alertService().showHttpError(this, error.response);
        });
    }
  }

  public retrieveAssetLive(assetLiveId): void {
    this.assetLiveService()
      .find(assetLiveId)
      .then(res => {
        this.assetLive = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
