<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="shopDailyReportApp.borrowedDebtType.home.createOrEditLabel"
          data-cy="BorrowedDebtTypeCreateUpdateHeading"
          v-text="$t('shopDailyReportApp.borrowedDebtType.home.createOrEditLabel')"
        >
          Create or edit a BorrowedDebtType
        </h2>
        <div>
          <div class="form-group" v-if="borrowedDebtType.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="borrowedDebtType.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('shopDailyReportApp.borrowedDebtType.name')" for="borrowed-debt-type-name"
              >Name</label
            >
            <input
              type="text"
              class="form-control"
              name="name"
              id="borrowed-debt-type-name"
              data-cy="name"
              :class="{ valid: !$v.borrowedDebtType.name.$invalid, invalid: $v.borrowedDebtType.name.$invalid }"
              v-model="$v.borrowedDebtType.name.$model"
              required
            />
            <div v-if="$v.borrowedDebtType.name.$anyDirty && $v.borrowedDebtType.name.$invalid">
              <small class="form-text text-danger" v-if="!$v.borrowedDebtType.name.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('shopDailyReportApp.borrowedDebtType.code')" for="borrowed-debt-type-code"
              >Code</label
            >
            <input
              type="text"
              class="form-control"
              name="code"
              id="borrowed-debt-type-code"
              data-cy="code"
              :class="{ valid: !$v.borrowedDebtType.code.$invalid, invalid: $v.borrowedDebtType.code.$invalid }"
              v-model="$v.borrowedDebtType.code.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('shopDailyReportApp.borrowedDebtType.notes')" for="borrowed-debt-type-notes"
              >Notes</label
            >
            <input
              type="text"
              class="form-control"
              name="notes"
              id="borrowed-debt-type-notes"
              data-cy="notes"
              :class="{ valid: !$v.borrowedDebtType.notes.$invalid, invalid: $v.borrowedDebtType.notes.$invalid }"
              v-model="$v.borrowedDebtType.notes.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="$t('shopDailyReportApp.borrowedDebtType.createdDate')"
              for="borrowed-debt-type-createdDate"
              >Created Date</label
            >
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="borrowed-debt-type-createdDate"
                  v-model="$v.borrowedDebtType.createdDate.$model"
                  name="createdDate"
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
                id="borrowed-debt-type-createdDate"
                data-cy="createdDate"
                type="text"
                class="form-control"
                name="createdDate"
                :class="{ valid: !$v.borrowedDebtType.createdDate.$invalid, invalid: $v.borrowedDebtType.createdDate.$invalid }"
                v-model="$v.borrowedDebtType.createdDate.$model"
              />
            </b-input-group>
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
            :disabled="$v.borrowedDebtType.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./borrowed-debt-type-update.component.ts"></script>
