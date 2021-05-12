/*
 * Copyright 2008-2016 Andre Pfeiler
 *
 * This file is part of FindBugs-IDEA.
 *
 * FindBugs-IDEA is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * FindBugs-IDEA is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with FindBugs-IDEA.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.twodividedbyzero.idea.findbugs.actions;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.twodividedbyzero.idea.findbugs.core.AbstractSettings;
import org.twodividedbyzero.idea.findbugs.core.FindBugsState;
import org.twodividedbyzero.idea.findbugs.core.ProjectSettings;
import org.twodividedbyzero.idea.findbugs.core.WorkspaceSettings;
import org.twodividedbyzero.idea.findbugs.gui.toolwindow.view.ToolWindowPanel;
import org.twodividedbyzero.idea.findbugs.gui.tree.GroupBy;

import java.util.Arrays;

abstract class AbstractGroupByAction extends AbstractToggleAction {

	private final GroupBy _groupBy;

	AbstractGroupByAction(@NotNull final GroupBy groupBy) {
		_groupBy = groupBy;
	}

	@Override
	final boolean isSelectedImpl(
			@NotNull final AnActionEvent e,
			@NotNull final Project project,
			@Nullable final Module module,
			@NotNull final ToolWindow toolWindow,
			@NotNull final ToolWindowPanel panel,
			@NotNull final FindBugsState state,
			@NotNull final ProjectSettings projectSettings,
			@NotNull final AbstractSettings settings
	) {

		final WorkspaceSettings workspaceSettings = WorkspaceSettings.getInstance(project);
		final String groupByProperty = workspaceSettings.toolWindowGroupBy;
		final boolean equals = _groupBy.name().equals(groupByProperty);
		final GroupBy[] sortOrderGroup = GroupBy.getSortOrderGroup(_groupBy);
		if (equals && !Arrays.equals(panel.getBugTreePanel().getGroupBy(), sortOrderGroup)) {
			panel.getBugTreePanel().setGroupBy(sortOrderGroup);
		}
		return equals;
	}

	@Override
	final void setSelectedImpl(
			@NotNull final AnActionEvent e,
			@NotNull final Project project,
			@Nullable final Module module,
			@NotNull final ToolWindow toolWindow,
			@NotNull final ToolWindowPanel panel,
			@NotNull final FindBugsState state,
			@NotNull final ProjectSettings projectSettings,
			@NotNull final AbstractSettings settings,
			final boolean select
	) {

		if (select) {
			final WorkspaceSettings workspaceSettings = WorkspaceSettings.getInstance(project);
			workspaceSettings.toolWindowGroupBy = _groupBy.name();
			panel.getBugTreePanel().setGroupBy(GroupBy.getSortOrderGroup(_groupBy));
		}
	}
}
