<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="shopDailyReportApp.expenseType.home.createOrEditLabel"
          data-cy="ExpenseTypeCreateUpdateHeading"
          v-text="$t('shopDailyReportApp.expenseType.home.createOrEditLabel')"
        >
          Create or edit a ExpenseType
        </h2>
        <div>
          <div class="form-group" v-if="expenseType.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="expenseType.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('shopDailyReportApp.expenseType.name')" for="expense-type-name">Name</label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="expense-type-name"
              data-cy="name"
              :class="{ valid: !$v.expenseType.name.$invalid, invalid: $v.expenseType.name.$invalid }"
              v-model="$v.expenseType.name.$model"
              required
            />
            <div v-if="$v.expenseType.name.$anyDirty && $v.expenseType.name.$invalid">
              <small class="form-text text-danger" v-if="!$v.expenseType.name.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('shopDailyReportApp.expenseType.code')" for="expense-type-code">Code</label>
            <input
              type="text"
              class="form-control"
              name="code"
              id="expense-type-code"
              data-cy="code"
              :class="{ valid: !$v.expenseType.code.$invalid, invalid: $v.expenseType.code.$invalid }"
              v-model="$v.expenseType.code.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('shopDailyReportApp.expenseType.notes')" for="expense-type-notes">Notes</label>
            <input
              type="text"
              class="form-control"
              name="notes"
              id="expense-type-notes"
              data-cy="notes"
              :class="{ valid: !$v.expenseType.notes.$invalid, invalid: $v.expenseType.notes.$invalid }"
              v-model="$v.expenseType.notes.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('shopDailyReportApp.expenseType.createdDate')" for="expense-type-createdDate"
              >Created Date</label
            >
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="expense-type-createdDate"
                  v-model="$v.expenseType.createdDate.$model"
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
                id="expense-type-createdDate"
                data-cy="createdDate"
                type="text"
                class="form-control"
                name="createdDate"
                :class="{ valid: !$v.expenseType.createdDate.$invalid, invalid: $v.expenseType.createdDate.$invalid }"
                v-model="$v.expenseType.createdDate.$model"
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
            :disabled="$v.expenseType.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./expense-type-update.component.ts"></script>
