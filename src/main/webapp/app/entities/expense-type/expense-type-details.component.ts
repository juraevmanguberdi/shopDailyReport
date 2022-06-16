import { Component, Vue, Inject } from 'vue-property-decorator';

import { IExpenseType } from '@/shared/model/expense-type.model';
import ExpenseTypeService from './expense-type.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class ExpenseTypeDetails extends Vue {
  @Inject('expenseTypeService') private expenseTypeService: () => ExpenseTypeService;
  @Inject('alertService') private alertService: () => AlertService;

  public expenseType: IExpenseType = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.expenseTypeId) {
        vm.retrieveExpenseType(to.params.expenseTypeId);
      }
    });
  }

  public retrieveExpenseType(expenseTypeId) {
    this.expenseTypeService()
      .find(expenseTypeId)
      .then(res => {
        this.expenseType = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
