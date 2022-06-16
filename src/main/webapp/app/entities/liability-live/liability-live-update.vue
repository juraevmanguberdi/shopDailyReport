<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="shopDailyReportApp.liabilityLive.home.createOrEditLabel"
          data-cy="LiabilityLiveCreateUpdateHeading"
          v-text="$t('shopDailyReportApp.liabilityLive.home.createOrEditLabel')"
        >
          Create or edit a LiabilityLive
        </h2>
        <div>
          <div class="form-group" v-if="liabilityLive.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="liabilityLive.id" readonly />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="$t('shopDailyReportApp.liabilityLive.totalLiabilities')"
              for="liability-live-totalLiabilities"
              >Total Liabilities</label
            >
            <input
              type="number"
              class="form-control"
              name="totalLiabilities"
              id="liability-live-totalLiabilities"
              data-cy="totalLiabilities"
              :class="{ valid: !$v.liabilityLive.totalLiabilities.$invalid, invalid: $v.liabilityLive.totalLiabilities.$invalid }"
              v-model.number="$v.liabilityLive.totalLiabilities.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('shopDailyReportApp.liabilityLive.supplier')" for="liability-live-supplier"
              >Supplier</label
            >
            <input
              type="number"
              class="form-control"
              name="supplier"
              id="liability-live-supplier"
              data-cy="supplier"
              :class="{ valid: !$v.liabilityLive.supplier.$invalid, invalid: $v.liabilityLive.supplier.$invalid }"
              v-model.number="$v.liabilityLive.supplier.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('shopDailyReportApp.liabilityLive.bankLoan')" for="liability-live-bankLoan"
              >Bank Loan</label
            >
            <input
              type="number"
              class="form-control"
              name="bankLoan"
              id="liability-live-bankLoan"
              data-cy="bankLoan"
              :class="{ valid: !$v.liabilityLive.bankLoan.$invalid, invalid: $v.liabilityLive.bankLoan.$invalid }"
              v-model.number="$v.liabilityLive.bankLoan.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('shopDailyReportApp.liabilityLive.other')" for="liability-live-other">Other</label>
            <input
              type="number"
              class="form-control"
              name="other"
              id="liability-live-other"
              data-cy="other"
              :class="{ valid: !$v.liabilityLive.other.$invalid, invalid: $v.liabilityLive.other.$invalid }"
              v-model.number="$v.liabilityLive.other.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('shopDailyReportApp.liabilityLive.code')" for="liability-live-code">Code</label>
            <input
              type="text"
              class="form-control"
              name="code"
              id="liability-live-code"
              data-cy="code"
              :class="{ valid: !$v.liabilityLive.code.$invalid, invalid: $v.liabilityLive.code.$invalid }"
              v-model="$v.liabilityLive.code.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('shopDailyReportApp.liabilityLive.notes')" for="liability-live-notes">Notes</label>
            <input
              type="text"
              class="form-control"
              name="notes"
              id="liability-live-notes"
              data-cy="notes"
              :class="{ valid: !$v.liabilityLive.notes.$invalid, invalid: $v.liabilityLive.notes.$invalid }"
              v-model="$v.liabilityLive.notes.$model"
            />
          </div>
        </div>
        <div>
          <button type="button" id="cancel-save" data-cy="entityCreateCancelButton" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="$v.liabilityLive.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./liability-live-update.component.ts"></script>
