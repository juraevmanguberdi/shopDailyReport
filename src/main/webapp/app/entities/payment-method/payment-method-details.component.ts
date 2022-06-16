import { Component, Vue, Inject } from 'vue-property-decorator';

import { IPaymentMethod } from '@/shared/model/payment-method.model';
import PaymentMethodService from './payment-method.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class PaymentMethodDetails extends Vue {
  @Inject('paymentMethodService') private paymentMethodService: () => PaymentMethodService;
  @Inject('alertService') private alertService: () => AlertService;

  public paymentMethod: IPaymentMethod = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.paymentMethodId) {
        vm.retrievePaymentMethod(to.params.paymentMethodId);
      }
    });
  }

  public retrievePaymentMethod(paymentMethodId) {
    this.paymentMethodService()
      .find(paymentMethodId)
      .then(res => {
        this.paymentMethod = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
