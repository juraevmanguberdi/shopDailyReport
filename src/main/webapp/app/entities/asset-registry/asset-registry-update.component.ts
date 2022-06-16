import { Component, Vue, Inject } from 'vue-property-decorator';

import { required } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';

import { IAssetRegistry, AssetRegistry } from '@/shared/model/asset-registry.model';
import AssetRegistryService from './asset-registry.service';

const validations: any = {
  assetRegistry: {
    today: {
      required,
    },
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
export default class AssetRegistryUpdate extends Vue {
  @Inject('assetRegistryService') private assetRegistryService: () => AssetRegistryService;
  @Inject('alertService') private alertService: () => AlertService;

  public assetRegistry: IAssetRegistry = new AssetRegistry();
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.assetRegistryId) {
        vm.retrieveAssetRegistry(to.params.assetRegistryId);
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
    if (this.assetRegistry.id) {
      this.assetRegistryService()
        .update(this.assetRegistry)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('shopDailyReportApp.assetRegistry.updated', { param: param.id });
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
      this.assetRegistryService()
        .create(this.assetRegistry)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('shopDailyReportApp.assetRegistry.created', { param: param.id });
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

  public retrieveAssetRegistry(assetRegistryId): void {
    this.assetRegistryService()
      .find(assetRegistryId)
      .then(res => {
        this.assetRegistry = res;
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
