import { Component, Vue, Inject } from 'vue-property-decorator';

import { required } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';

import { IBorrowedDebtType, BorrowedDebtType } from '@/shared/model/borrowed-debt-type.model';
import BorrowedDebtTypeService from './borrowed-debt-type.service';

const validations: any = {
  borrowedDebtType: {
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
export default class BorrowedDebtTypeUpdate extends Vue {
  @Inject('borrowedDebtTypeService') private borrowedDebtTypeService: () => BorrowedDebtTypeService;
  @Inject('alertService') private alertService: () => AlertService;

  public borrowedDebtType: IBorrowedDebtType = new BorrowedDebtType();
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.borrowedDebtTypeId) {
        vm.retrieveBorrowedDebtType(to.params.borrowedDebtTypeId);
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
    if (this.borrowedDebtType.id) {
      this.borrowedDebtTypeService()
        .update(this.borrowedDebtType)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('shopDailyReportApp.borrowedDebtType.updated', { param: param.id });
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
      this.borrowedDebtTypeService()
        .create(this.borrowedDebtType)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('shopDailyReportApp.borrowedDebtType.created', { param: param.id });
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

  public retrieveBorrowedDebtType(borrowedDebtTypeId): void {
    this.borrowedDebtTypeService()
      .find(borrowedDebtTypeId)
      .then(res => {
        this.borrowedDebtType = res;
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
