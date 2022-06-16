import { Component, Vue, Inject } from 'vue-property-decorator';

import { IDailyRegistryShop } from '@/shared/model/daily-registry-shop.model';
import DailyRegistryShopService from './daily-registry-shop.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class DailyRegistryShopDetails extends Vue {
  @Inject('dailyRegistryShopService') private dailyRegistryShopService: () => DailyRegistryShopService;
  @Inject('alertService') private alertService: () => AlertService;

  public dailyRegistryShop: IDailyRegistryShop = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.dailyRegistryShopId) {
        vm.retrieveDailyRegistryShop(to.params.dailyRegistryShopId);
      }
    });
  }

  public retrieveDailyRegistryShop(dailyRegistryShopId) {
    this.dailyRegistryShopService()
      .find(dailyRegistryShopId)
      .then(res => {
        this.dailyRegistryShop = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
