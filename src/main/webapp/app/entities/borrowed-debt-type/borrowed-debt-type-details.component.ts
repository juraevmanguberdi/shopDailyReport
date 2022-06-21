import { Component, Vue, Inject } from 'vue-property-decorator';

import { IBorrowedDebtType } from '@/shared/model/borrowed-debt-type.model';
import BorrowedDebtTypeService from './borrowed-debt-type.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class BorrowedDebtTypeDetails extends Vue {
  @Inject('borrowedDebtTypeService') private borrowedDebtTypeService: () => BorrowedDebtTypeService;
  @Inject('alertService') private alertService: () => AlertService;

  public borrowedDebtType: IBorrowedDebtType = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.borrowedDebtTypeId) {
        vm.retrieveBorrowedDebtType(to.params.borrowedDebtTypeId);
      }
    });
  }

  public retrieveBorrowedDebtType(borrowedDebtTypeId) {
    this.borrowedDebtTypeService()
      .find(borrowedDebtTypeId)
      .then(res => {
        this.borrowedDebtType = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
