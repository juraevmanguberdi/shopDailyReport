import { Component, Vue, Inject } from 'vue-property-decorator';

import { required } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';

import { IExpenseType, ExpenseType } from '@/shared/model/expense-type.model';
import ExpenseTypeService from './expense-type.service';

const validations: any = {
  expenseType: {
    name: {
      required,
    },
    code: {},
    notes: {},
    createdDate: {},
  },
};

@Component({
  validations,
})
export default class ExpenseTypeUpdate extends Vue {
  @Inject('expenseTypeService') private expenseTypeService: () => ExpenseTypeService;
  @Inject('alertService') private alertService: () => AlertService;

  public expenseType: IExpenseType = new ExpenseType();
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.expenseTypeId) {
        vm.retrieveExpenseType(to.params.expenseTypeId);
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
    if (this.expenseType.id) {
      this.expenseTypeService()
        .update(this.expenseType)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('shopDailyReportApp.expenseType.updated', { param: param.id });
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
      this.expenseTypeService()
        .create(this.expenseType)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('shopDailyReportApp.expenseType.created', { param: param.id });
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

  public retrieveExpenseType(expenseTypeId): void {
    this.expenseTypeService()
      .find(expenseTypeId)
      .then(res => {
        this.expenseType = res;
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
