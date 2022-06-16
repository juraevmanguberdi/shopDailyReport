import { Component, Vue, Inject } from 'vue-property-decorator';

import { IOwnerExpenseType } from '@/shared/model/owner-expense-type.model';
import OwnerExpenseTypeService from './owner-expense-type.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class OwnerExpenseTypeDetails extends Vue {
  @Inject('ownerExpenseTypeService') private ownerExpenseTypeService: () => OwnerExpenseTypeService;
  @Inject('alertService') private alertService: () => AlertService;

  public ownerExpenseType: IOwnerExpenseType = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.ownerExpenseTypeId) {
        vm.retrieveOwnerExpenseType(to.params.ownerExpenseTypeId);
      }
    });
  }

  public retrieveOwnerExpenseType(ownerExpenseTypeId) {
    this.ownerExpenseTypeService()
      .find(ownerExpenseTypeId)
      .then(res => {
        this.ownerExpenseType = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
