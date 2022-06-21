import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';

import BorrowedDebtTypeService from '@/entities/borrowed-debt-type/borrowed-debt-type.service';
import { IBorrowedDebtType } from '@/shared/model/borrowed-debt-type.model';

import { IBorrowedDebt, BorrowedDebt } from '@/shared/model/borrowed-debt.model';
import BorrowedDebtService from './borrowed-debt.service';

const validations: any = {
  borrowedDebt: {
    amount: {
      required,
      numeric,
    },
    notes: {},
    code: {},
    date: {},
    borrowedDebtType: {
      required,
    },
  },
};

@Component({
  validations,
})
export default class BorrowedDebtUpdate extends Vue {
  @Inject('borrowedDebtService') private borrowedDebtService: () => BorrowedDebtService;
  @Inject('alertService') private alertService: () => AlertService;

  public borrowedDebt: IBorrowedDebt = new BorrowedDebt();

  @Inject('borrowedDebtTypeService') private borrowedDebtTypeService: () => BorrowedDebtTypeService;

  public borrowedDebtTypes: IBorrowedDebtType[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.borrowedDebtId) {
        vm.retrieveBorrowedDebt(to.params.borrowedDebtId);
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
    if (this.borrowedDebt.id) {
      this.borrowedDebtService()
        .update(this.borrowedDebt)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('shopDailyReportApp.borrowedDebt.updated', { param: param.id });
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
      this.borrowedDebtService()
        .create(this.borrowedDebt)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('shopDailyReportApp.borrowedDebt.created', { param: param.id });
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

  public retrieveBorrowedDebt(borrowedDebtId): void {
    this.borrowedDebtService()
      .find(borrowedDebtId)
      .then(res => {
        this.borrowedDebt = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.borrowedDebtTypeService()
      .retrieve()
      .then(res => {
        this.borrowedDebtTypes = res.data;
      });
  }
}
