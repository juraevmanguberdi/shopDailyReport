import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';

import ClientService from '@/entities/client/client.service';
import { IClient } from '@/shared/model/client.model';

import { IDebtGiven, DebtGiven } from '@/shared/model/debt-given.model';
import DebtGivenService from './debt-given.service';

const validations: any = {
  debtGiven: {
    debtAmount: {
      required,
      numeric,
    },
    debtDate: {
      required,
    },
    returnDate: {
      required,
    },
    code: {},
    notes: {},
    client: {
      required,
    },
  },
};

@Component({
  validations,
})
export default class DebtGivenUpdate extends Vue {
  @Inject('debtGivenService') private debtGivenService: () => DebtGivenService;
  @Inject('alertService') private alertService: () => AlertService;

  public debtGiven: IDebtGiven = new DebtGiven();

  @Inject('clientService') private clientService: () => ClientService;

  public clients: IClient[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.debtGivenId) {
        vm.retrieveDebtGiven(to.params.debtGivenId);
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
    if (this.debtGiven.id) {
      this.debtGivenService()
        .update(this.debtGiven)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('shopDailyReportApp.debtGiven.updated', { param: param.id });
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
      this.debtGivenService()
        .create(this.debtGiven)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('shopDailyReportApp.debtGiven.created', { param: param.id });
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

  public retrieveDebtGiven(debtGivenId): void {
    this.debtGivenService()
      .find(debtGivenId)
      .then(res => {
        this.debtGiven = res;
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
  }
}
