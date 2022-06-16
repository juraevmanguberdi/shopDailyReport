<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="shopDailyReportApp.liabilityRegistry.home.createOrEditLabel"
          data-cy="LiabilityRegistryCreateUpdateHeading"
          v-text="$t('shopDailyReportApp.liabilityRegistry.home.createOrEditLabel')"
        >
          Create or edit a LiabilityRegistry
        </h2>
        <div>
          <div class="form-group" v-if="liabilityRegistry.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="liabilityRegistry.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('shopDailyReportApp.liabilityRegistry.today')" for="liability-registry-today"
              >Today</label
            >
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="liability-registry-today"
                  v-model="$v.liabilityRegistry.today.$model"
                  name="today"
                  class="form-control"
                  :locale="currentLanguage"
                  button-only
                  today-button
                  reset-button
                  close-button
                >
                </b-form-datepicker>
              </b-input-group-prepend>
              <b-form-input
                id="liability-registry-today"
                data-cy="today"
                type="text"
                class="form-control"
                name="today"
                :class="{ valid: !$v.liabilityRegistry.today.$invalid, invalid: $v.liabilityRegistry.today.$invalid }"
                v-model="$v.liabilityRegistry.today.$model"
                required
              />
            </b-input-group>
            <div v-if="$v.liabilityRegistry.today.$anyDirty && $v.liabilityRegistry.today.$invalid">
              <small class="form-text text-danger" v-if="!$v.liabilityRegistry.today.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="$t('shopDailyReportApp.liabilityRegistry.totalLiabilities')"
              for="liability-registry-totalLiabilities"
              >Total Liabilities</label
            >
            <input
              type="number"
              class="form-control"
              name="totalLiabilities"
              id="liability-registry-totalLiabilities"
              data-cy="totalLiabilities"
              :class="{ valid: !$v.liabilityRegistry.totalLiabilities.$invalid, invalid: $v.liabilityRegistry.totalLiabilities.$invalid }"
              v-model.number="$v.liabilityRegistry.totalLiabilities.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('shopDailyReportApp.liabilityRegistry.supplier')" for="liability-registry-supplier"
              >Supplier</label
            >
            <input
              type="number"
              class="form-control"
              name="supplier"
              id="liability-registry-supplier"
              data-cy="supplier"
              :class="{ valid: !$v.liabilityRegistry.supplier.$invalid, invalid: $v.liabilityRegistry.supplier.$invalid }"
              v-model.number="$v.liabilityRegistry.supplier.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('shopDailyReportApp.liabilityRegistry.bankLoan')" for="liability-registry-bankLoan"
              >Bank Loan</label
            >
            <input
              type="number"
              class="form-control"
              name="bankLoan"
              id="liability-registry-bankLoan"
              data-cy="bankLoan"
              :class="{ valid: !$v.liabilityRegistry.bankLoan.$invalid, invalid: $v.liabilityRegistry.bankLoan.$invalid }"
              v-model.number="$v.liabilityRegistry.bankLoan.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('shopDailyReportApp.liabilityRegistry.other')" for="liability-registry-other"
              >Other</label
            >
            <input
              type="number"
              class="form-control"
              name="other"
              id="liability-registry-other"
              data-cy="other"
              :class="{ valid: !$v.liabilityRegistry.other.$invalid, invalid: $v.liabilityRegistry.other.$invalid }"
              v-model.number="$v.liabilityRegistry.other.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('shopDailyReportApp.liabilityRegistry.code')" for="liability-registry-code"
              >Code</label
            >
            <input
              type="text"
              class="form-control"
              name="code"
              id="liability-registry-code"
              data-cy="code"
              :class="{ valid: !$v.liabilityRegistry.code.$invalid, invalid: $v.liabilityRegistry.code.$invalid }"
              v-model="$v.liabilityRegistry.code.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('shopDailyReportApp.liabilityRegistry.notes')" for="liability-registry-notes"
              >Notes</label
            >
            <input
              type="text"
              class="form-control"
              name="notes"
              id="liability-registry-notes"
              data-cy="notes"
              :class="{ valid: !$v.liabilityRegistry.notes.$invalid, invalid: $v.liabilityRegistry.notes.$invalid }"
              v-model="$v.liabilityRegistry.notes.$model"
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
            :disabled="$v.liabilityRegistry.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./liability-registry-update.component.ts"></script>
