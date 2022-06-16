import { Component, Vue, Inject } from 'vue-property-decorator';

import { required } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';

import { IPaymentMethod, PaymentMethod } from '@/shared/model/payment-method.model';
import PaymentMethodService from './payment-method.service';

const validations: any = {
  paymentMethod: {
    name: {
      required,
    },
    code: {},
    note: {},
  },
};

@Component({
  validations,
})
export default class PaymentMethodUpdate extends Vue {
  @Inject('paymentMethodService') private paymentMethodService: () => PaymentMethodService;
  @Inject('alertService') private alertService: () => AlertService;

  public paymentMethod: IPaymentMethod = new PaymentMethod();
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.paymentMethodId) {
        vm.retrievePaymentMethod(to.params.paymentMethodId);
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
    if (this.paymentMethod.id) {
      this.paymentMethodService()
        .update(this.paymentMethod)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('shopDailyReportApp.paymentMethod.updated', { param: param.id });
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
      this.paymentMethodService()
        .create(this.paymentMethod)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('shopDailyReportApp.paymentMethod.created', { param: param.id });
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

  public retrievePaymentMethod(paymentMethodId): void {
    this.paymentMethodService()
      .find(paymentMethodId)
      .then(res => {
        this.paymentMethod = res;
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
