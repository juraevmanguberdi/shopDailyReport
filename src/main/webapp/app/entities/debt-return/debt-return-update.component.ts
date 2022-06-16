import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';

import ClientService from '@/entities/client/client.service';
import { IClient } from '@/shared/model/client.model';

import PaymentMethodService from '@/entities/payment-method/payment-method.service';
import { IPaymentMethod } from '@/shared/model/payment-method.model';

import { IDebtReturn, DebtReturn } from '@/shared/model/debt-return.model';
import DebtReturnService from './debt-return.service';

const validations: any = {
  debtReturn: {
    returnAmount: {
      required,
      numeric,
    },
    returnDate: {
      required,
    },
    code: {},
    client: {
      required,
    },
    paymentMethod: {
      required,
    },
  },
};

@Component({
  validations,
})
export default class DebtReturnUpdate extends Vue {
  @Inject('debtReturnService') private debtReturnService: () => DebtReturnService;
  @Inject('alertService') private alertService: () => AlertService;

  public debtReturn: IDebtReturn = new DebtReturn();

  @Inject('clientService') private clientService: () => ClientService;

  public clients: IClient[] = [];

  @Inject('paymentMethodService') private paymentMethodService: () => PaymentMethodService;

  public paymentMethods: IPaymentMethod[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.debtReturnId) {
        vm.retrieveDebtReturn(to.params.debtReturnId);
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
    if (this.debtReturn.id) {
      this.debtReturnService()
        .update(this.debtReturn)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('shopDailyReportApp.debtReturn.updated', { param: param.id });
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
      this.debtReturnService()
        .create(this.debtReturn)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('shopDailyReportApp.debtReturn.created', { param: param.id });
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

  public retrieveDebtReturn(debtReturnId): void {
    this.debtReturnService()
      .find(debtReturnId)
      .then(res => {
        this.debtReturn = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.clientService()
      .retrieve()
      .then(res => {
        this.clients = res.data;
      });
    this.paymentMethodService()
      .retrieve()
      .then(res => {
        this.paymentMethods = res.data;
      });
  }
}
