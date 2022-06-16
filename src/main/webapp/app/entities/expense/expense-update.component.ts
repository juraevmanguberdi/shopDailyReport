import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';

import ExpenseTypeService from '@/entities/expense-type/expense-type.service';
import { IExpenseType } from '@/shared/model/expense-type.model';

import { IExpense, Expense } from '@/shared/model/expense.model';
import ExpenseService from './expense.service';

const validations: any = {
  expense: {
    amount: {
      required,
      numeric,
    },
    expenseDate: {},
    notes: {},
    code: {},
    expenseType: {
      required,
    },
  },
};

@Component({
  validations,
})
export default class ExpenseUpdate extends Vue {
  @Inject('expenseService') private expenseService: () => ExpenseService;
  @Inject('alertService') private alertService: () => AlertService;

  public expense: IExpense = new Expense();

  @Inject('expenseTypeService') private expenseTypeService: () => ExpenseTypeService;

  public expenseTypes: IExpenseType[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.expenseId) {
        vm.retrieveExpense(to.params.expenseId);
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
    if (this.expense.id) {
      this.expenseService()
        .update(this.expense)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('shopDailyReportApp.expense.updated', { param: param.id });
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
      this.expenseService()
        .create(this.expense)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('shopDailyReportApp.expense.created', { param: param.id });
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

  public retrieveExpense(expenseId): void {
    this.expenseService()
      .find(expenseId)
      .then(res => {
        this.expense = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.expenseTypeService()
      .retrieve()
      .then(res => {
        this.expenseTypes = res.data;
      });
  }
}
