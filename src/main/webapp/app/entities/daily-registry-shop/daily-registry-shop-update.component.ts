import { Component, Vue, Inject } from 'vue-property-decorator';

import { required, numeric } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';

import { IDailyRegistryShop, DailyRegistryShop } from '@/shared/model/daily-registry-shop.model';
import DailyRegistryShopService from './daily-registry-shop.service';

const validations: any = {
  dailyRegistryShop: {
    today: {
      required,
    },
    sales: {
      required,
      numeric,
    },
    goods: {
      required,
      numeric,
    },
    cashShop: {},
    click: {},
    terminal: {},
    debtReturn: {},
    debtGiven: {},
    expense: {},
    balance: {},
    code: {},
  },
};

@Component({
  validations,
})
export default class DailyRegistryShopUpdate extends Vue {
  @Inject('dailyRegistryShopService') private dailyRegistryShopService: () => DailyRegistryShopService;
  @Inject('alertService') private alertService: () => AlertService;

  public dailyRegistryShop: IDailyRegistryShop = new DailyRegistryShop();
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.dailyRegistryShopId) {
        vm.retrieveDailyRegistryShop(to.params.dailyRegistryShopId);
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
    if (this.dailyRegistryShop.id) {
      this.dailyRegistryShopService()
        .update(this.dailyRegistryShop)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('shopDailyReportApp.dailyRegistryShop.updated', { param: param.id });
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
      this.dailyRegistryShopService()
        .create(this.dailyRegistryShop)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('shopDailyReportApp.dailyRegistryShop.created', { param: param.id });
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

  public retrieveDailyRegistryShop(dailyRegistryShopId): void {
    this.dailyRegistryShopService()
      .find(dailyRegistryShopId)
      .then(res => {
        this.dailyRegistryShop = res;
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
