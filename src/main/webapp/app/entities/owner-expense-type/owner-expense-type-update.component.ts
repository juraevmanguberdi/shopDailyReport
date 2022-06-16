import { Component, Vue, Inject } from 'vue-property-decorator';

import { required } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';

import { IOwnerExpenseType, OwnerExpenseType } from '@/shared/model/owner-expense-type.model';
import OwnerExpenseTypeService from './owner-expense-type.service';

const validations: any = {
  ownerExpenseType: {
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
export default class OwnerExpenseTypeUpdate extends Vue {
  @Inject('ownerExpenseTypeService') private ownerExpenseTypeService: () => OwnerExpenseTypeService;
  @Inject('alertService') private alertService: () => AlertService;

  public ownerExpenseType: IOwnerExpenseType = new OwnerExpenseType();
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.ownerExpenseTypeId) {
        vm.retrieveOwnerExpenseType(to.params.ownerExpenseTypeId);
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
    if (this.ownerExpenseType.id) {
      this.ownerExpenseTypeService()
        .update(this.ownerExpenseType)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('shopDailyReportApp.ownerExpenseType.updated', { param: param.id });
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
      this.ownerExpenseTypeService()
        .create(this.ownerExpenseType)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('shopDailyReportApp.ownerExpenseType.created', { param: param.id });
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

  public retrieveOwnerExpenseType(ownerExpenseTypeId): void {
    this.ownerExpenseTypeService()
      .find(ownerExpenseTypeId)
      .then(res => {
        this.ownerExpenseType = res;
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
