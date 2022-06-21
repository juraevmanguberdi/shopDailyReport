import { Component, Vue, Inject } from 'vue-property-decorator';

import { IRequiredProductType } from '@/shared/model/required-product-type.model';
import RequiredProductTypeService from './required-product-type.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class RequiredProductTypeDetails extends Vue {
  @Inject('requiredProductTypeService') private requiredProductTypeService: () => RequiredProductTypeService;
  @Inject('alertService') private alertService: () => AlertService;

  public requiredProductType: IRequiredProductType = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.requiredProductTypeId) {
        vm.retrieveRequiredProductType(to.params.requiredProductTypeId);
      }
    });
  }

  public retrieveRequiredProductType(requiredProductTypeId) {
    this.requiredProductTypeService()
      .find(requiredProductTypeId)
      .then(res => {
        this.requiredProductType = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
