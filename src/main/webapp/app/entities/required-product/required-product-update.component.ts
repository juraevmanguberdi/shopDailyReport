import { Component, Vue, Inject } from 'vue-property-decorator';

import { required } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';

import RequiredProductTypeService from '@/entities/required-product-type/required-product-type.service';
import { IRequiredProductType } from '@/shared/model/required-product-type.model';

import { IRequiredProduct, RequiredProduct } from '@/shared/model/required-product.model';
import RequiredProductService from './required-product.service';

const validations: any = {
  requiredProduct: {
    name: {
      required,
    },
    note: {},
    code: {},
    requiredProductType: {
      required,
    },
  },
};

@Component({
  validations,
})
export default class RequiredProductUpdate extends Vue {
  @Inject('requiredProductService') private requiredProductService: () => RequiredProductService;
  @Inject('alertService') private alertService: () => AlertService;

  public requiredProduct: IRequiredProduct = new RequiredProduct();

  @Inject('requiredProductTypeService') private requiredProductTypeService: () => RequiredProductTypeService;

  public requiredProductTypes: IRequiredProductType[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.requiredProductId) {
        vm.retrieveRequiredProduct(to.params.requiredProductId);
      }
      vm.initRelationships();
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
    if (this.requiredProduct.id) {
      this.requiredProductService()
        .update(this.requiredProduct)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('shopDailyReportApp.requiredProduct.updated', { param: param.id });
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
      this.requiredProductService()
        .create(this.requiredProduct)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('shopDailyReportApp.requiredProduct.created', { param: param.id });
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

  public retrieveRequiredProduct(requiredProductId): void {
    this.requiredProductService()
      .find(requiredProductId)
      .then(res => {
        this.requiredProduct = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.requiredProductTypeService()
      .retrieve()
      .then(res => {
        this.requiredProductTypes = res.data;
      });
  }
}
