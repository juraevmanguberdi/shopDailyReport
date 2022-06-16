import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';

import OwnerExpenseTypeService from '@/entities/owner-expense-type/owner-expense-type.service';
import { IOwnerExpenseType } from '@/shared/model/owner-expense-type.model';

import { IOwnerExpense, OwnerExpense } from '@/shared/model/owner-expense.model';
import OwnerExpenseService from './owner-expense.service';

const validations: any = {
  ownerExpense: {
    amount: {
      required,
      numeric,
    },
    code: {},
    expenseDate: {},
    notes: {},
    ownerExpenseType: {
      required,
    },
  },
};

@Component({
  validations,
})
export default class OwnerExpenseUpdate extends Vue {
  @Inject('ownerExpenseService') private ownerExpenseService: () => OwnerExpenseService;
  @Inject('alertService') private alertService: () => AlertService;

  public ownerExpense: IOwnerExpense = new OwnerExpense();

  @Inject('ownerExpenseTypeService') private ownerExpenseTypeService: () => OwnerExpenseTypeService;

  public ownerExpenseTypes: IOwnerExpenseType[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.ownerExpenseId) {
        vm.retrieveOwnerExpense(to.params.ownerExpenseId);
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
    if (this.ownerExpense.id) {
      this.ownerExpenseService()
        .update(this.ownerExpense)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('shopDailyReportApp.ownerExpense.updated', { param: param.id });
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
      this.ownerExpenseService()
        .create(this.ownerExpense)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('shopDailyReportApp.ownerExpense.created', { param: param.id });
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

  public retrieveOwnerExpense(ownerExpenseId): void {
    this.ownerExpenseService()
      .find(ownerExpenseId)
      .then(res => {
        this.ownerExpense = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.ownerExpenseTypeService()
      .retrieve()
      .then(res => {
        this.ownerExpenseTypes = res.data;
      });
  }
}
