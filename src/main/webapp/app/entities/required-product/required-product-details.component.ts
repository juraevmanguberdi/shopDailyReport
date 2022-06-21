import { Component, Vue, Inject } from 'vue-property-decorator';

import { IRequiredProduct } from '@/shared/model/required-product.model';
import RequiredProductService from './required-product.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class RequiredProductDetails extends Vue {
  @Inject('requiredProductService') private requiredProductService: () => RequiredProductService;
  @Inject('alertService') private alertService: () => AlertService;

  public requiredProduct: IRequiredProduct = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.requiredProductId) {
        vm.retrieveRequiredProduct(to.params.requiredProductId);
      }
    });
  }

  public retrieveRequiredProduct(requiredProductId) {
    this.requiredProductService()
      .find(requiredProductId)
      .then(res => {
        this.requiredProduct = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
