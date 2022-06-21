import { Component, Vue, Inject } from 'vue-property-decorator';

import { required } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';

import { IRequiredProductType, RequiredProductType } from '@/shared/model/required-product-type.model';
import RequiredProductTypeService from './required-product-type.service';

const validations: any = {
  requiredProductType: {
    name: {
      required,
    },
    code: {},
    notes: {},
  },
};

@Component({
  validations,
})
export default class RequiredProductTypeUpdate extends Vue {
  @Inject('requiredProductTypeService') private requiredProductTypeService: () => RequiredProductTypeService;
  @Inject('alertService') private alertService: () => AlertService;

  public requiredProductType: IRequiredProductType = new RequiredProductType();
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.requiredProductTypeId) {
        vm.retrieveRequiredProductType(to.params.requiredProductTypeId);
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
    if (this.requiredProductType.id) {
      this.requiredProductTypeService()
        .update(this.requiredProductType)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('shopDailyReportApp.requiredProductType.updated', { param: param.id });
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
      this.requiredProductTypeService()
        .create(this.requiredProductType)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('shopDailyReportApp.requiredProductType.created', { param: param.id });
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

  public retrieveRequiredProductType(requiredProductTypeId): void {
    this.requiredProductTypeService()
      .find(requiredProductTypeId)
      .then(res => {
        this.requiredProductType = res;
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
